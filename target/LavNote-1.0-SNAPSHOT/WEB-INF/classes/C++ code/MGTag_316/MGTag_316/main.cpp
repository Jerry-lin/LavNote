#include <sys/time.h>
#include <algorithm>
#include <cstdio>
#include <cstdlib>
#include <iterator>
#include <utility>
#include <vector>
#include <deque>
#include <string.h>
#include <iostream>
#include <assert.h>
#include <set>
#define K 5
#define D (320 * K)


namespace bs {
    
    using namespace std;
    
    struct node {
        int N_O_SZ;//, N_I_SZ;
        int *N_O;// *N_I;
        int indegree;
        int root_layer;
        int root_located;
    };
    
    struct label1{
        int oldID_mapto_newID;
        pair<int, int> Level;
    };
    
    struct label2{
        int rl;
        int rlt;
        int interval;
        vector<int> excpID;
        vector<int> id_cross_arr;
    };
    
    struct label3
    {
        vector<int> N_I, N_O;
        int vis;
        union {
            int L_in[K];
            unsigned char h_in;
        };
        union {
            int L_out[K];
            unsigned char h_out;
        };
        pair<int, int> L_interval;
    };
    
    vector<node> nodes;
    struct label1 *nodeLabel1;
    struct label2 *nodeLabel2;
    struct label3 *nodeLabel3;
    
    int oldID_mapto_newID_count;
    
    vector<int> TopoOrder;
    vector<int> TopoOrder_cross;
    vector<int> cross_id;
    
    vector< vector<int> > excpID_O;
    vector< vector<int> > id_cross_arr_pro;
    
    vector<int> temp_root_arr;
    int *reach_flag;
    int querycnt;
    int traverseCnt;
    int vis_cur;
    int cur;
    
    void read_graph(const char *filename) {
        timeval start_at, end_at;
        gettimeofday(&start_at, 0);
        FILE *file = fopen(filename, "r");
        char header[] = "graph_for_greach";
        fscanf(file, "%s", header);
        int n;
        fscanf(file, "%d", &n);
        nodes.resize(n);
        vector< vector<int> > N_O(n);//, N_I(n);
        for (;;) {
            int u, v;
            if (feof(file) || fscanf(file, "%d", &u) != 1) {
                break;
            }
            fgetc(file);
            while (!feof(file) && fscanf(file, "%d", &v) == 1) {
                N_O[u].push_back(v);
                
                nodes[v].indegree++;
            }
            fgetc(file);
        }
        fclose(file);
        for (int u = 0; u < n; u++) {
            nodes[u].N_O_SZ = N_O[u].size();
            nodes[u].N_O = new int[N_O[u].size()];
            sort(N_O[u].begin(), N_O[u].end());
            copy(N_O[u].begin(), N_O[u].end(), nodes[u].N_O);
            //nodes[u].N_I_SZ = N_I[u].size();
            //nodes[u].N_I = new int[N_I[u].size()];
            //copy(N_I[u].begin(), N_I[u].end(), nodes[u].N_I);
        }
        gettimeofday(&end_at, 0);
        printf("read time(graph): %.3fs\n",
               end_at.tv_sec - start_at.tv_sec +
               double(end_at.tv_usec - start_at.tv_usec) / 1000000);
    }
    
    void pre_process(bool *&cross_vertices_arr, int *&nextInDegree){
        //TopoOrder.resize(maxID + 1);
        
        int i = 0, k = TopoOrder.size();
        int layer = 1;
        //ID component;
        
        bool flag=true;
        int vectorsize = k;
        
        while(flag){
            flag=false;
            layer++;
            //cout<<"vectorsize:"<<vectorsize<<endl;
            for(; i<vectorsize; i++){
                int u = TopoOrder[i];
                
                for(int j=0; j<nodes[u].N_O_SZ; j++){
                    int addr = nodes[u].N_O[j];
                    nodes[addr].indegree --;
                    
                    if(cross_vertices_arr[u]){
                        cross_vertices_arr[addr] = true;
                        nodes[addr].root_located = 0; // add
                        
                        nextInDegree[addr] ++;
                        //degree[addr[j]] = 0;
                    }
                    
                    else if(nodes[addr].root_located != 0){// && root_located[addr[j]] != root_located[u]){
                        if(nodes[addr].root_located != nodes[u].root_located && !cross_vertices_arr[addr]){
                            cross_vertices_arr[addr] = true;
                            cross_id.push_back(addr);
                            
                            nodes[addr].root_located = 0; // add
                            //degree[addr[j]] = 0;
                        }
                    }
                    else if(!cross_vertices_arr[addr]){ //add
                        nodes[addr].root_located = nodes[u].root_located;
                        //root_layer[addr[j]] = oldID_mapto_newID_count;
                    }
                    
                    if(nodes[addr].indegree == 0){
                        TopoOrder.push_back(addr);
                        nodeLabel1[addr].Level.second = layer;
                        k++;
                    }
                }
            }
            
            if(vectorsize!=k){
                vectorsize=k;
                flag=true;
            }
        }
        
        //cout<<"TopoOrder:"<<TopoOrder.size()<<" "<<"cross size:"<<cross_id.size()<<endl;
        
    }
    
    
    void pre_process_cross(bool *&cross_vertices_arr, int *&curInDegree, int *&nextInDegree){
        int i = 0, k = TopoOrder_cross.size();
        
        bool flag=true;
        int vectorsize = k;
        cross_id.clear();
        while(flag){
            flag=false;
            //cout<<"vectorsize:"<<vectorsize<<endl;
            for(; i<vectorsize; i++){
                int u = TopoOrder_cross[i];
                
                for(int j=0; j<nodes[u].N_O_SZ; j++){
                    int addr = nodes[u].N_O[j];
                    curInDegree[addr] --;
                    
                    if(cross_vertices_arr[u]){
                        cross_vertices_arr[addr] = true;
                        nodes[addr].root_located = 0; // add
                        
                        nextInDegree[addr] ++;
                    }
                    
                    else if(nodes[addr].root_located != 0){// && root_located[addr[j]] != root_located[u]){
                        if(nodes[addr].root_located != nodes[u].root_located && !cross_vertices_arr[addr]){
                            cross_vertices_arr[addr] = true;
                            cross_id.push_back(addr);
                            
                            nodes[addr].root_located = 0; // add
                            //degree[addr[j]].indeg = 0;
                        }
                    }
                    else if(!cross_vertices_arr[addr]){ //add
                        nodes[addr].root_located = nodes[u].root_located;
                        //root_layer[addr[j]] = oldID_mapto_newID_count;
                    }
                    
                    if(curInDegree[addr] == 0){
                        TopoOrder_cross.push_back(addr);
                        k++;
                    }
                }
            }
            
            if(vectorsize!=k){
                vectorsize=k;
                flag=true;
            }
        }
        
        //cout<<"TopoOrder:"<<TopoOrder_cross.size()<<" "<<"cross size:"<<cross_id.size()<<endl;
    }
    
    void cal_cross_degree(bool *&visited_arr, int *&curInDegree){//, unsigned *&nextInDegree){
        int len = temp_root_arr.size();
        deque<int>* que = new deque<int>();
        for(int i = 0; i < len; i ++){
            que->push_front(temp_root_arr[i]);
            while(!que->empty()){
                int u = que->front();
                que->pop_front();
                
                for(int j = 0; j < nodes[u].N_O_SZ; j ++){
                    int addr = nodes[u].N_O[j];
                    if(nodes[addr].root_located == nodes[u].root_located){
                        curInDegree[addr]++;
                        if(!visited_arr[addr]){
                            visited_arr[addr] = true;
                            que->push_back(addr);
                        }
                    }
                }
            }
        }
        delete que;
        que = NULL;
    }
    
    void decode(int *&curInDegree){
        deque<int>* que = new deque<int>();
        
        int rootNum = temp_root_arr.size();
        int idcount = 0;
        for(int index = 0;index < rootNum; index ++){
            que->push_front(temp_root_arr[index]);
            
            while(!que->empty()){
                int u = que->front();
                que->pop_front();
                
                idcount++;
                nodeLabel1[u].oldID_mapto_newID = idcount;
                nodeLabel2[idcount].rl = nodes[temp_root_arr[index]].root_layer;
                nodeLabel2[idcount].rlt = nodes[temp_root_arr[index]].root_located;
                
                for (int i = 0; i < nodes[u].N_O_SZ; i++) {
                    int addr = nodes[u].N_O[i];
                    if(nodes[addr].root_located == nodes[u].root_located){
                        if(curInDegree[addr]){
                            curInDegree[addr] --;
                        }
                        if(curInDegree[addr]){
                            //if(root_located[addr[i]] == temp_root_arr[index] + 1)
                            excpID_O[u].push_back(addr);
                            continue;
                        }//degree if
                        que->push_front(addr);
                    }
                }//for
            } //while
        } //for
        
        delete que;
        que = NULL;
    }
    
    bool is_excp(int id1, int id2, int f , int l) {
        //int f = 0, l = excpID_O[id1].size() - 1;
        while(f <= l){
            int m = (f + l)/2;
            if(excpID_O[id1][m] == id2)return true;
            else if(excpID_O[id1][m] < id2)f = m + 1;
            else l = m - 1;
        }
        return false;
    }
    
    void cal_index(){
        //cout<<"topoorder size:"<<TopoOrder.size()<<endl;
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            int u = TopoOrder[i];
            
            unsigned tmpLup=0, tmpInterval = nodeLabel1[u].oldID_mapto_newID;
            int right = excpID_O[u].size() - 1;
            //ID temp_nor = oldID_mapto_newID[u];
            for(int j=0; j<nodes[u].N_O_SZ; j++){
                int addr = nodes[u].N_O[j];
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                //Lup
                if(nodeLabel1[addr].Level.first > tmpLup){
                    tmpLup = nodeLabel1[addr].Level.first;
                }
                
                //if(temp_nor < nor_interval[oldID_mapto_newID[addr[j]]])
                //    temp_nor = nor_interval[oldID_mapto_newID[addr[j]]];
                
                if(nodes[addr].root_located == nodes[u].root_located) {
                    //interval
                    if((right < 0 || !is_excp(u, addr, 0, right)) && tmpInterval < nodeLabel2[addr_newID].interval)
                        tmpInterval = nodeLabel2[addr_newID].interval;
                }
                //else //if(cross_located[u][cross_located_count] == 0)
                //id_cross_arr_pro[u].push_back(addr[j]);
            }
            nodeLabel1[u].Level.first = tmpLup+1;
            nodeLabel2[nodeLabel1[u].oldID_mapto_newID].interval = tmpInterval;
            //nor_interval[oldID_mapto_newID[u]] = temp_nor;
        }
    }
    
    bool same_tree_reach(int id1, int id2){
        if(nodeLabel2[id1].interval >= nodeLabel2[id2].interval){
            return true;
        }
        else {
            int len = nodeLabel2[id1].excpID.size();
            for(int i = 0; i < len; i ++) {
                reach_flag[nodeLabel2[id1].excpID[i]]=querycnt;
                //if(oldID_mapto_newID[excpID[id1][i]] > oldID_mapto_newID[id2])
                if(nodeLabel2[id1].excpID[i] > id2)
                    return false;
                else if(nodeLabel2[nodeLabel2[id1].excpID[i]].interval >= nodeLabel2[id2].interval) {
                    return true;
                }
            } //for
        }
        
        return false;
    }
    
    bool is_reachable(int id1, int id2, int len){
        for(int i = 0; i < len; i ++){
            int id = id_cross_arr_pro[id1][i];
            if(reach_flag[id] == querycnt)continue;
            //if(rl[id] == rl[id2]){
            if(nodeLabel2[id].rlt == nodeLabel2[id2].rlt){
                if(id > id2){
                    if(same_tree_reach(id2, id)){
                        reach_flag[id] = querycnt;
                    }
                }
                else{
                    if(same_tree_reach(id, id2)){
                        reach_flag[id2] = querycnt;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    void cal_excp_and_cross_arr(){
        reach_flag = (int *)calloc((nodes.size() + 1), sizeof(int));
        int *exit_flag = (int *)calloc((nodes.size() + 1), sizeof(int));
        querycnt = -1;
        //bool flag;
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            int u = TopoOrder[i];
            //u的新id
            int u_newID = nodeLabel1[u].oldID_mapto_newID;
            
            querycnt += 2;
            int sz = excpID_O[u].size();
            //int cnt = 0;
            //用于判断异常点
            for(int n = 0; n < sz; n ++){
                int newID = nodeLabel1[excpID_O[u][n]].oldID_mapto_newID;
                if(nodeLabel2[u_newID].interval < nodeLabel2[newID].interval /*&& exit_flag[newID] != querycnt*/){
                    nodeLabel2[u_newID].excpID.push_back(newID);
                    exit_flag[newID] = querycnt;
                }
            }
            
            for(int j=0; j<nodes[u].N_O_SZ; j++){
                //子节点
                int addr = nodes[u].N_O[j];
                //子节点新id
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                int len = id_cross_arr_pro[u_newID].size();
                if(nodeLabel2[addr_newID].rlt == nodeLabel2[u_newID].rlt) {
                    //excpID
                    sz = nodeLabel2[addr_newID].excpID.size();
                    //将判断子节点的异常点是否是父节点异常点，如果是添加，如果不是，不处理
                    for(int n = 0; n < sz; n ++){
                        if(nodeLabel2[u_newID].interval < nodeLabel2[nodeLabel2[addr_newID].excpID[n]].interval &&
                           exit_flag[nodeLabel2[addr_newID].excpID[n]] != querycnt){
                            nodeLabel2[u_newID].excpID.push_back(nodeLabel2[addr_newID].excpID[n]);
                            exit_flag[nodeLabel2[addr_newID].excpID[n]] = querycnt;
                        }
                    }
                    sz = nodeLabel2[addr_newID].id_cross_arr.size();
                    //int len = id_cross_arr_pro[u].size();
                    for(int n = 0; n < sz; n ++){
                        if(exit_flag[nodeLabel2[addr_newID].id_cross_arr[n]] == querycnt + 1 ||
                           reach_flag[nodeLabel2[addr_newID].id_cross_arr[n]] == querycnt)
                            continue;
                        
                        if(!is_reachable(u_newID, nodeLabel2[addr_newID].id_cross_arr[n], len)){
                            
                            id_cross_arr_pro[u_newID].push_back(nodeLabel2[addr_newID].id_cross_arr[n]);
                            exit_flag[nodeLabel2[addr_newID].id_cross_arr[n]] = querycnt + 1;
                        }
                    }
                }
                else {
                    if(exit_flag[addr_newID] != querycnt + 1 && reach_flag[addr_newID] != querycnt &&
                       !is_reachable(u_newID, addr_newID, len)){
                        id_cross_arr_pro[u_newID].push_back(addr_newID);
                        exit_flag[addr_newID] = querycnt + 1;
                    }
                }
            }
            
            sz = id_cross_arr_pro[u_newID].size();
            for(int j = 0; j < sz; j ++){
                if(reach_flag[id_cross_arr_pro[u_newID][j]] != querycnt)
                    nodeLabel2[u_newID].id_cross_arr.push_back(id_cross_arr_pro[u_newID][j]);
            }
            if(nodeLabel2[u_newID].id_cross_arr.size() > 0)
                sort(nodeLabel2[u_newID].id_cross_arr.begin(), nodeLabel2[u_newID].id_cross_arr.end());//, cmpNewID);
            if(nodeLabel2[u_newID].excpID.size() > 0)
                sort(nodeLabel2[u_newID].excpID.begin(), nodeLabel2[u_newID].excpID.end());//, cmpNewID);
           
        }
        //统一存储新的id
        for (int i=1; i<=nodes.size(); i++) {
            // root点旧id
            int u_root=nodeLabel2[i].rlt-1;
            // xin的id
            int u_root_new=nodeLabel1[u_root].oldID_mapto_newID;
            for (int j=0; j<nodeLabel2[i].id_cross_arr.size(); j++) {
                //如果跨子图点的id等于它的根节点的id，那么就将父节点的root节点的out存入，反过来将跨子图根节点存入父节点id
                int v_root=nodeLabel2[nodeLabel2[i].id_cross_arr[j]].rlt-1;
                int v_root_new=nodeLabel1[v_root].oldID_mapto_newID;
                if(nodeLabel2[i].id_cross_arr[j]==v_root_new)
                {
                    nodeLabel3[u_root_new].N_O.push_back(v_root_new);
                    nodeLabel3[v_root_new].N_I.push_back(u_root_new);
                }
            }
        }
        //去重
        for (int i=1; i<=nodes.size(); i++) {
            sort(nodeLabel3[i].N_O.begin(),nodeLabel3[i].N_O.end());
            nodeLabel3[i].N_O.erase(unique(nodeLabel3[i].N_O.begin(),nodeLabel3[i].N_O.end()), nodeLabel3[i].N_O.end());
            sort(nodeLabel3[i].N_I.begin(),nodeLabel3[i].N_I.end());
            nodeLabel3[i].N_I.erase(unique(nodeLabel3[i].N_I.begin(),nodeLabel3[i].N_I.end()), nodeLabel3[i].N_I.end());
        }
        
        
    }
    
    int h_in() {
        static int c = 0, r = rand();
        //当c大于nodes.size()/D会重新编号
        if (c >= (int) nodes.size() / D) {
            c = 0;
            r = rand();
        }
        //每次调用此函数都会c++
        c++;
        return r;
    }
    
    int h_out() {
        static int c = 0, r = rand();
        if (c >= (int) nodes.size() / D) {
            c = 0;
            r = rand();
        }
        c++;
        return r;
    }
    
    void dfs_in(label3 &u) {
        u.vis = vis_cur;
        
        if (u.N_I.empty()) {
            u.h_in = h_in() % (K * 32);
        } else {
            for (int i = 0; i < K; i++) {
                u.L_in[i] = 0;
            }
            
            for (int i = 0; i < u.N_I.size(); i++) {
                label3 &v = nodeLabel3[u.N_I[i]];
                if (v.vis != vis_cur) {
                    dfs_in(v);
                }
                if (v.N_I.empty()) {
                    int hu = v.h_in;
                    u.L_in[(hu >> 5) % K] |= 1 << (hu & 31);
                } else {
                    for (int j = 0; j < K; j++) {
                        u.L_in[j] |= v.L_in[j];
                    }
                }
            }
            
            int hu = h_in();
            u.L_in[(hu >> 5) % K] |= 1 << (hu & 31);
        }
    }
    
    void dfs_out(label3 &u) {
        u.vis = vis_cur;
        
        u.L_interval.first = cur++;
        
        if (u.N_O.empty()) {
            u.h_out = h_out() % (K * 32);
        } else {
            for (int i = 0; i < K; i++) {
                u.L_out[i] = 0;
            }
            
            for (int i = 0; i < u.N_O.size(); i++) {
                label3 &v = nodeLabel3[u.N_O[i]];
                if (v.vis != vis_cur) {
                    dfs_out(v);
                }
                if (v.N_O.empty()) {
                    int hu = v.h_out;
                    u.L_out[(hu >> 5) % K] |= 1 << (hu & 31);
                } else {
                    for (int j = 0; j < K; j++) {
                        u.L_out[j] |= v.L_out[j];
                    }
                }
            }
            
            int hu = h_out();
            u.L_out[(hu >> 5) % K] |= 1 << (hu & 31);
        }
        
        u.L_interval.second = cur;
    }
    
    void calculate_bloom_filter()
    {
        vis_cur++;
        for (int i = 1; i <=nodes.size(); i++) {
            if (nodeLabel3[i].N_I.size()!=0&&nodeLabel3[i].N_O.size() == 0) {
                dfs_in(nodeLabel3[i]);
            }
        }
        vis_cur++;
        cur = 0;
        for (int i = 1; i <=nodes.size(); i++) {
            if (nodeLabel3[i].N_O.size()!=0&&nodeLabel3[i].N_I.size() == 0) {
                dfs_out(nodeLabel3[i]);
            }
        }
    }
    
    
    void index_construction() {
        timeval start_time, end_time;
        gettimeofday(&start_time, 0);
        
        for (int u = 0; u < nodes.size(); u++) {
            if(nodes[u].indegree == 0){
                temp_root_arr.push_back(u);
            }
        }
        
        nodeLabel1 = (struct label1 *)calloc(nodes.size(), sizeof(struct label1));
        nodeLabel2 = (struct label2 *)calloc(nodes.size() + 1, sizeof(struct label2));
        nodeLabel3 = (struct label3 *)calloc(nodes.size() + 1, sizeof(struct label3));
        
        int *curInDegree =  (int *)calloc(nodes.size(), sizeof(int));
        int *nextInDegree = (int *)calloc(nodes.size(), sizeof(int));
        bool *cross_vertices_arr = (bool *)calloc(nodes.size(), sizeof(bool));
        oldID_mapto_newID_count = 1;
        
        for(int i = 0; i < temp_root_arr.size(); i ++){
            nodeLabel1[temp_root_arr[i]].Level.second = 1;
            
            TopoOrder.push_back(temp_root_arr[i]);
            nodes[temp_root_arr[i]].root_located = temp_root_arr[i] + 1;
            
            nodes[temp_root_arr[i]].root_layer = oldID_mapto_newID_count;
        }
        
        memset(cross_vertices_arr, false, sizeof(bool) * nodes.size());
        pre_process(cross_vertices_arr, curInDegree);
        
        while(cross_id.size() != 0){
            TopoOrder_cross.clear();
            oldID_mapto_newID_count++;
            
            for(int i = 0; i < cross_id.size(); i ++){
                if(curInDegree[cross_id[i]] == 0/* && degree[cross_id[i]].outdeg != 0*/){
                    temp_root_arr.push_back(cross_id[i]);
                    TopoOrder_cross.push_back(cross_id[i]);
                    nodes[cross_id[i]].root_located = cross_id[i] + 1;
                    
                    nodes[cross_id[i]].root_layer = oldID_mapto_newID_count;
                }
            }
            
            memset(cross_vertices_arr, false, sizeof(bool) * nodes.size());
            pre_process_cross(cross_vertices_arr, curInDegree, nextInDegree);
            int* temp = curInDegree;
            curInDegree = nextInDegree;
            nextInDegree = temp;
        }
        
        memset(cross_vertices_arr, false, sizeof(bool) * nodes.size());
        cal_cross_degree(cross_vertices_arr, curInDegree);//, nextInDegree);
        
        excpID_O = vector< vector<int> >(nodes.size());
        decode(curInDegree);
        
        cal_index();
        
        id_cross_arr_pro = vector< vector<int> >(nodes.size()+1);
        cal_excp_and_cross_arr();
        calculate_bloom_filter();
        
        for (int i=1; i<=nodes.size(); i++) {
            cout<<"旧id:"<<i-1<<"|新id:";
            int new_id=nodeLabel1[i-1].oldID_mapto_newID;
            cout<<new_id<<"|跨子图点:";
            for (int j=0; j<nodeLabel2[new_id].id_cross_arr.size();j++) {
                int root=nodeLabel2[nodeLabel2[new_id].id_cross_arr[j]].rlt;
                int root_new=nodeLabel1[root-1].oldID_mapto_newID;
                cout<<nodeLabel2[new_id].id_cross_arr[j]<<"根节点旧值:"<<root-1<<"根节点："<<root_new<<"--";
            }
            cout<<endl;
        }
        /*
        cout<<"这是计算的新的label3："<<endl;
        for(int i=1; i<nodes.size()+1; i++) {
            cout<<nodeLabel1[i-1].oldID_mapto_newID<<":";
            for (int j=0; j<nodeLabel3[nodeLabel1[i-1].oldID_mapto_newID].N_O.size(); j++) {
                cout<<nodeLabel3[nodeLabel1[i-1].oldID_mapto_newID].N_O[j]<<" ";
            }
            cout<<endl;
        }*/
        
        gettimeofday(&end_time, 0);
        printf("index time: %.3fms\n",
               (end_time.tv_sec - start_time.tv_sec) * 1000 +
               double(end_time.tv_usec - start_time.tv_usec) / 1000);
        
        cout<<"total root layer: "<<oldID_mapto_newID_count<<endl;
        //space
        int totalsize = 5 * nodes.size();
        for(int i = 1; i <= nodes.size(); i ++){
            totalsize += nodeLabel2[i].excpID.size() + nodeLabel2[i].id_cross_arr.size();
        }
        long long index_size = 0;
        for (int u = 0; u <=nodes.size(); u++) {
            index_size +=
            nodeLabel3[u].N_I.empty() ?
            sizeof(nodeLabel3[u].h_in) : sizeof(nodeLabel3[u].L_in);
            index_size +=
            nodeLabel3[u].N_O.empty() ?
            sizeof(nodeLabel3[u].h_out) : sizeof(nodeLabel3[u].L_out);
            index_size += sizeof(nodeLabel3[u].L_interval);
        }
        totalsize += oldID_mapto_newID_count + temp_root_arr.size();
        cout<<"index size (MB): "<<(double)(totalsize*4+index_size)/(1024*1024)<<endl;
        cout<<"index size (KB): "<<(double)(totalsize*4+index_size)/1024<<endl;
    }
    
    vector<int> src;
    vector<int> dest;
    
    void read_queries(const char *filename) {
        timeval start_at, end_at;
        gettimeofday(&start_at, 0);
        FILE *file = fopen(filename, "r");
        int u, v, r;
        while (fscanf(file, "%d%d%d", &u, &v, &r) == 3) {
            src.push_back(u);
            dest.push_back(v);
        }
        fclose(file);
        gettimeofday(&end_at, 0);
        printf("read time(query): %.3fs\n",
               end_at.tv_sec - start_at.tv_sec +
               double(end_at.tv_usec - start_at.tv_usec) / 1000000);
    }
    
    bool calculate_root_reach(int rlt1,int rlt2)
    {
        //int rlt1_new=nodeLabel1[rlt1].oldID_mapto_newID;
        //int rlt2_new=nodeLabel1[rlt2].oldID_mapto_newID;
        if (nodeLabel3[rlt1].L_interval.second < nodeLabel3[rlt2].L_interval.second) {
            return false;
        } else if (nodeLabel3[rlt1].L_interval.first <= nodeLabel3[rlt2].L_interval.first) {
            return true;
        }
        
        if (nodeLabel3[rlt2].N_I.empty()) {
            return false;
        }
        if (nodeLabel3[rlt1].N_O.empty()) {
            return false;
        }
        if (nodeLabel3[rlt2].N_O.empty()) {
            if ((nodeLabel3[rlt1].L_out[nodeLabel3[rlt2].h_out >> 5] & (1 << (nodeLabel3[rlt2].h_out & 31))) == 0) {
                return false;
            }
        } else {
            for (int i = 0; i < K; i++) {
                if ((nodeLabel3[rlt1].L_out[i] & nodeLabel3[rlt2].L_out[i]) != nodeLabel3[rlt2].L_out[i]) {
                    return false;
                }
            }
        }
        if (nodeLabel3[rlt1].N_I.empty()) {
            if ((nodeLabel3[rlt2].L_in[nodeLabel3[rlt1].h_in >> 5] & (1 << (nodeLabel3[rlt1].h_in & 31))) == 0) {
                return false;
            }
        } else {
            for (int i = 0; i < K; i++) {
                if ((nodeLabel3[rlt1].L_in[i] & nodeLabel3[rlt2].L_in[i]) != nodeLabel3[rlt1].L_in[i]) {
                    return false;
                }
            }
        }
        
        for (int i=0; i<nodeLabel3[rlt1].N_O.size(); i++) {
            if(nodeLabel3[nodeLabel3[rlt1].N_O[i]].vis!=vis_cur)
            {
                nodeLabel3[nodeLabel3[rlt1].N_O[i]].vis=vis_cur;
            }
            if(calculate_root_reach(nodeLabel3[rlt1].N_O[i], rlt2))
                return true;
        }
        
        return false;
    }
    
    bool same_layer_tree_reach1(int id1, int id2){
        //reach_flag[id1] = querycnt;
        if(nodeLabel2[id1].rlt != nodeLabel2[id2].rlt){
            return false;
        }
        else{
            if(nodeLabel2[id1].interval >= nodeLabel2[id2].interval){
                return true;
            }
            else {
                int len = nodeLabel2[id1].excpID.size();
                for(int i = 0; i < len; i ++) {
                    if(nodeLabel2[id1].excpID[i] > id2)
                        return false;
                    else if(nodeLabel2[nodeLabel2[id1].excpID[i]].interval >= nodeLabel2[id2].interval) {
                        
                        return true;
                    }
                } //for
                
                return false;
            }
        }
    }
    
    bool down_layer_to_up_layer_without_cross_located1(int id1, int id2){
        int len = nodeLabel2[id1].id_cross_arr.size();
    
        for(int i = 0; i < len; i ++){
            if(nodeLabel2[id1].id_cross_arr[i] > id2)
                return false;
            //else if(reach_flag[nodeLabel2[id1].id_cross_arr[i]] == querycnt)continue;
            else{
                
                //reach_flag[nodeLabel2[id1].id_cross_arr[i]] = querycnt;
                if(nodeLabel2[nodeLabel2[id1].id_cross_arr[i]].rl < nodeLabel2[id2].rl){
                    if(!calculate_root_reach(nodeLabel1[nodeLabel2[id1].rlt-1].oldID_mapto_newID,nodeLabel1[nodeLabel2[id2].rlt-1].oldID_mapto_newID))   return false;
                    traverseCnt ++;
                    if(down_layer_to_up_layer_without_cross_located1(nodeLabel2[id1].id_cross_arr[i], id2))
                        return true;
                    
                }
                else {
                    if(same_layer_tree_reach1(nodeLabel2[id1].id_cross_arr[i], id2)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    bool query1(int id1, int id2){
        //    if(rl[id1] > rl[id2])
        //        return false;
        //    else
        if(nodeLabel2[id1].rl < nodeLabel2[id2].rl){
            return down_layer_to_up_layer_without_cross_located1(id1, id2);
        }
        else {
            return same_layer_tree_reach1(id1, id2);
        }
        return false;
    }
    
    bool same_layer_tree_reach(int id1, int id2){
        //reach_flag[id1] = querycnt;
        if(nodeLabel2[id1].rlt != nodeLabel2[id2].rlt){
            return false;
        }
        else{
            if(nodeLabel2[id1].interval >= nodeLabel2[id2].interval){
                return true;
            }
            else {
                int len = nodeLabel2[id1].excpID.size();
                for(int i = 0; i < len; i ++) {
                    if(nodeLabel2[id1].excpID[i] > id2)
                        return false;
                    else if(nodeLabel2[nodeLabel2[id1].excpID[i]].interval >= nodeLabel2[id2].interval) {
                        
                        return true;
                    }
                } //for
                
                return false;
            }
        }
    }
    
    bool down_layer_to_up_layer_without_cross_located(int id1, int id2){

        int len = nodeLabel2[id1].id_cross_arr.size();
    
        for(int i = 0; i < len; i ++){
            if(nodeLabel2[id1].id_cross_arr[i] > id2)
                return false;
            //    if(nodeLabel2[id1].id_cross_arr[i] == id2)
            //        return true;
            //else if(reach_flag[nodeLabel2[id1].id_cross_arr[i]] == querycnt)continue;
            else{
                
                //reach_flag[nodeLabel2[id1].id_cross_arr[i]] = querycnt;
                if(nodeLabel2[nodeLabel2[id1].id_cross_arr[i]].rl < nodeLabel2[id2].rl){
                    if(!calculate_root_reach(nodeLabel1[nodeLabel2[id1].rlt-1].oldID_mapto_newID,nodeLabel1[nodeLabel2[id2].rlt-1].oldID_mapto_newID))   return false;
                    //traverseCnt ++;
                    if(down_layer_to_up_layer_without_cross_located(nodeLabel2[id1].id_cross_arr[i], id2))
                        return true;
                    
                }
                else {
                    if(same_layer_tree_reach(nodeLabel2[id1].id_cross_arr[i], id2)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    bool query(int id1, int id2){
        //    if(rl[id1] > rl[id2])
        //        return false;
        //    else
        if(nodeLabel2[id1].rl < nodeLabel2[id2].rl){
            return down_layer_to_up_layer_without_cross_located(id1, id2);
        }
        else {
            return same_layer_tree_reach(id1, id2);
        }
        return false;
    }
    
    bool tranverse(int x, int y) {
        if(x == y) return 1;
        bool *visit = (bool *)calloc(nodes.size(), sizeof(bool));
        memset(visit, false, sizeof(bool) * nodes.size());
        deque<int>* que = new deque<int>();
        que->push_back(x);
        visit[x] = true;
        while(!que->empty()) {
            int curID = que->front();
            que->pop_front();
            
            for(int i = 0; i < nodes[curID].N_O_SZ; i ++) {
                int addr = nodes[curID].N_O[i];
                if(addr == y)
                    return 1;
                else if(! visit[addr]) {
                    que->push_back(addr);
                    visit[addr] = true;
                }
            }
        }
        return 0;
    }
    
    void run_queries() {
        timeval start_at, end_at;
        bool r;
        
        //memset(reach_flag, 0, sizeof(int) * nodes.size());
        //querycnt = 0;
        
        cout<<"query num:"<<src.size()<<endl;
        traverseCnt = 0;
        gettimeofday(&start_at, 0);
        for(vector<int>::iterator x=src.begin(), y=dest.begin(); x!=src.end(); x++, y++) {
            vis_cur++;
            //cout<<*x<<":"<<nodeLabel2[nodeLabel1[*x].oldID_mapto_newID].rlt<<endl;
            if(*x == *y)
                r = true;
            else if(nodeLabel1[*x].Level.second >= nodeLabel1[*y].Level.second ||
                    nodeLabel1[*x].Level.first <= nodeLabel1[*y].Level.first)
                r = false;
            else if(!calculate_root_reach(nodeLabel1[nodeLabel2[nodeLabel1[*x].oldID_mapto_newID].rlt-1].oldID_mapto_newID,nodeLabel1[nodeLabel2[nodeLabel1[*y].oldID_mapto_newID].rlt-1].oldID_mapto_newID)) r=false;
            else{
                if(nodeLabel1[*x].oldID_mapto_newID > nodeLabel1[*y].oldID_mapto_newID)
                    r = false;
                else r = query(nodeLabel1[*x].oldID_mapto_newID, nodeLabel1[*y].oldID_mapto_newID);
            }
        }
        gettimeofday(&end_at, 0);
        
        printf("*****query time******: %.3fms\n",
               (end_at.tv_sec - start_at.tv_sec) * 1000 +
               double(end_at.tv_usec - start_at.tv_usec) / 1000);
        
        int reachable = 0, nonreachable =0;
        int falsepositive = 0, falsenegative = 0;
        gettimeofday(&start_at, 0);
        for(vector<int>::iterator x=src.begin(), y=dest.begin(); x!=src.end(); x++, y++) {
            vis_cur++;
            if(*x == *y)
                r = true;
            else if(nodeLabel1[*x].Level.second >= nodeLabel1[*y].Level.second ||
                    nodeLabel1[*x].Level.first <= nodeLabel1[*y].Level.first)
                r = false;
            else if(!calculate_root_reach(nodeLabel1[nodeLabel2[nodeLabel1[*x].oldID_mapto_newID].rlt-1].oldID_mapto_newID,nodeLabel1[nodeLabel2[nodeLabel1[*y].oldID_mapto_newID].rlt-1].oldID_mapto_newID)) r=false;
            else{
                if(nodeLabel1[*x].oldID_mapto_newID > nodeLabel1[*y].oldID_mapto_newID)
                    r = false;
                else
                    r = query1(nodeLabel1[*x].oldID_mapto_newID, nodeLabel1[*y].oldID_mapto_newID);
            }
            
            if(r == true){
                reachable ++;
                //            if(tranverse(*x, *y) != 1){
                //                cout<<"wrong answer"<<endl;
                //                cout<<*x<<" "<<*y<<endl;
                //                falsepositive ++;
                //            }
            }
            else {
                //            if(tranverse(*x, *y) != 0){
                //                //cout<<"wrong answer"<<endl;
                //                falsenegative ++;
                //            }
                nonreachable ++;
            }
        }
        gettimeofday(&end_at, 0);
        cout<<"reachable: "<<reachable<<"  nonreach: "<<nonreachable<<endl;
        cout<<"falsepositive: "<<falsepositive<<"falsenegative: "<<falsenegative<<endl;
        cout<<"traverse num: "<<traverseCnt<<" average: "<<(double)traverseCnt/(1000000)<<endl;
        printf("query time: %.3fms\n",
               (end_at.tv_sec - start_at.tv_sec) * 1000 +
               double(end_at.tv_usec - start_at.tv_usec) / 1000);
    }
    
}

int main(int argc, char *argv[]) {
    using namespace bs;
    
    //read_graph(argv[1]);
    read_graph("/Users/youyujie/Documents/graph");
    
    index_construction();
    //read_queries(argv[2]);
    read_queries("/Users/youyujie/Documents/query");
    
    run_queries();
    
    return 0;
}


