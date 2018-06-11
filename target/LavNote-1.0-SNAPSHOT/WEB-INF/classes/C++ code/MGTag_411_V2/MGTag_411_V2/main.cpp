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

namespace bs {
    
    using namespace std;
    
    struct node {
        int N_O_SZ;//, N_I_SZ;
        int *N_O;// *N_I;
        int indegree;
        int root_layer;
        int root_located;
        
        //逆向分层
        int N_O_SZ_UP;
        int *N_O_UP;
        int indegree_up;
        int root_layer_up;
        int root_located_up;
        int oldID_mapto_newID;
    };
    
    struct label1{
        int oldID_mapto_newID;
        //first表示向上索引，second表示向下索引
        pair<int, int> Level;
    };
    
    struct label2{
        int rl;
        int rlt;
        int interval;
        vector<int> excpID;
        vector<int> id_cross_arr;
    };
    
    struct label3{
        int rl;
        int rlt;
        int interval;
        vector<int> excpID;
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
    int count;
    
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
        vector< vector<int> > N_O_UP(n);
        for (;;) {
            int u, v;
            if (feof(file) || fscanf(file, "%d", &u) != 1) {
                break;
            }
            fgetc(file);
            while (!feof(file) && fscanf(file, "%d", &v) == 1) {
                N_O[u].push_back(v);
                //N_I[v].push_back(u);
                
                nodes[v].indegree++;
                
                //逆向分层
                N_O_UP[v].push_back(u);
                nodes[u].indegree_up++;
            }
            fgetc(file);
        }
        fclose(file);
        for (int u = 0; u < n; u++) {
            nodes[u].N_O_SZ = N_O[u].size();
            nodes[u].N_O = new int[N_O[u].size()];
            sort(N_O[u].begin(), N_O[u].end());
            copy(N_O[u].begin(), N_O[u].end(), nodes[u].N_O);
            //逆向分层
            nodes[u].N_O_SZ_UP = N_O_UP[u].size();
            nodes[u].N_O_UP = new int[N_O_UP[u].size()];
            copy(N_O_UP[u].begin(), N_O_UP[u].end(), nodes[u].N_O_UP);
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
        //按照拓扑顺序进行遍历
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            //u的旧id
            int u = TopoOrder[i];
            //初始的间隔域
            unsigned tmpLup=0, tmpInterval =nodeLabel1[u].oldID_mapto_newID;
            //int up=nodeLabel1[u].Level.first;
            //改点的异常点长度
            int right = excpID_O[u].size() - 1;
            //ID temp_nor = oldID_mapto_newID[u];
            //遍历u的子节点
            for(int j=0; j<nodes[u].N_O_SZ; j++){
                //u的子节点
                int addr = nodes[u].N_O[j];
                //子节点新的id
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                //如果子节点向上索引大于0
                if(nodeLabel1[addr].Level.first > tmpLup){
                    tmpLup = nodeLabel1[addr].Level.first;
                }
                
                if(nodes[addr].root_located == nodes[u].root_located) {
                    //interval
                    if((right < 0 || !is_excp(u, addr, 0, right)) && tmpInterval < nodeLabel2[addr_newID].interval)
                        tmpInterval = nodeLabel2[addr_newID].interval;
                }
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
        //按照拓扑序列遍历
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            //旧id
            int u = TopoOrder[i];
            //新id
            int u_newID = nodeLabel1[u].oldID_mapto_newID;
            querycnt += 2;
            //可能异常点个数
            int sz = excpID_O[u].size();
            //遍历可能异常点
            for(int n = 0; n < sz; n ++){
                //可能异常点的新id
                int newID = nodeLabel1[excpID_O[u][n]].oldID_mapto_newID;
                //如果可能异常点的间隔域大于父节点,那么这个点事非树边
                if(nodeLabel2[u_newID].interval < nodeLabel2[newID].interval /*&& exit_flag[newID] != querycnt*/){
                    nodeLabel2[u_newID].excpID.push_back(newID);
                    //exit_flag[newID] = querycnt;
                }
            }
            //遍历u的子节点
            for(int j=0; j<nodes[u].N_O_SZ; j++){
                //子节点旧id
                int addr = nodes[u].N_O[j];
                //子节点新id
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                //uid的跨子图点大小
                int len = id_cross_arr_pro[u_newID].size();
                //如果子节点和父节点根一样相
                if(nodeLabel2[addr_newID].rlt == nodeLabel2[u_newID].rlt) {
                    //excpID
                    sz = nodeLabel2[addr_newID].excpID.size();
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
        
    }
    
    void index_down_constuction()
    {
        for (int u = 0; u < nodes.size(); u++) {
            if(nodes[u].indegree == 0){
                temp_root_arr.push_back(u);
            }
        }
        
        nodeLabel1 = (struct label1 *)calloc(nodes.size(), sizeof(struct label1));
        nodeLabel2 = (struct label2 *)calloc(nodes.size() + 1, sizeof(struct label2));
        
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
        
    }
    
    
    void pre_process_up(bool *&cross_vertices_arr, int *&nextInDegree){
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
                
                for(int j=0; j<nodes[u].N_O_SZ_UP; j++){
                    int addr = nodes[u].N_O_UP[j];
                    nodes[addr].indegree_up --;
                    
                    if(cross_vertices_arr[u]){
                        cross_vertices_arr[addr] = true;
                        nodes[addr].root_located_up= 0; // add
                        
                        nextInDegree[addr] ++;
                        //degree[addr[j]] = 0;
                    }
                    
                    else if(nodes[addr].root_located_up != 0){// && root_located[addr[j]] != root_located[u]){
                        if(nodes[addr].root_located_up != nodes[u].root_located_up && !cross_vertices_arr[addr]){
                            cross_vertices_arr[addr] = true;
                            cross_id.push_back(addr);
                            
                            nodes[addr].root_located_up = 0; // add
                            //degree[addr[j]] = 0;
                        }
                    }
                    else if(!cross_vertices_arr[addr]){ //add
                        nodes[addr].root_located_up = nodes[u].root_located_up;
                        //root_layer[addr[j]] = oldID_mapto_newID_count;
                    }
                    
                    if(nodes[addr].indegree_up == 0){
                        TopoOrder.push_back(addr);
                        //nodeLabel1[addr].Level.second = layer;
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
    
    
    void pre_process_cross_up(bool *&cross_vertices_arr, int *&curInDegree, int *&nextInDegree){
        int i = 0, k = TopoOrder_cross.size();
        
        bool flag=true;
        int vectorsize = k;
        cross_id.clear();
        while(flag){
            flag=false;
            //cout<<"vectorsize:"<<vectorsize<<endl;
            for(; i<vectorsize; i++){
                int u = TopoOrder_cross[i];
                
                for(int j=0; j<nodes[u].N_O_SZ_UP; j++){
                    int addr = nodes[u].N_O_UP[j];
                    curInDegree[addr] --;
                    
                    if(cross_vertices_arr[u]){
                        cross_vertices_arr[addr] = true;
                        nodes[addr].root_located_up= 0; // add
                        
                        nextInDegree[addr] ++;
                    }
                    
                    else if(nodes[addr].root_located_up!= 0){// && root_located[addr[j]] != root_located[u]){
                        if(nodes[addr].root_located_up!= nodes[u].root_located_up && !cross_vertices_arr[addr]){
                            cross_vertices_arr[addr] = true;
                            cross_id.push_back(addr);
                            
                            nodes[addr].root_located_up= 0; // add
                            //degree[addr[j]].indeg = 0;
                        }
                    }
                    else if(!cross_vertices_arr[addr]){ //add
                        nodes[addr].root_located_up= nodes[u].root_located_up;
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
    
    
    void decode_up(int *&curInDegree){
        deque<int>* que = new deque<int>();
        
        int rootNum = temp_root_arr.size();
        int idcount = 0;
        for(int index = 0;index < rootNum; index ++){
            que->push_front(temp_root_arr[index]);
            
            while(!que->empty()){
                int u = que->front();
                que->pop_front();
                idcount++;
                nodes[u].oldID_mapto_newID = idcount;
                nodeLabel3[nodeLabel1[u].oldID_mapto_newID].rl=nodes[temp_root_arr[index]].root_layer_up;
                nodeLabel3[nodeLabel1[u].oldID_mapto_newID].rlt=nodes[temp_root_arr[index]].root_located_up;
                for (int i = 0; i < nodes[u].N_O_SZ_UP; i++) {
                    int addr = nodes[u].N_O_UP[i];
                    if(nodes[addr].root_located_up == nodes[u].root_located_up){
                        if(curInDegree[addr]){
                            curInDegree[addr] --;
                        }
                        if(curInDegree[addr]){
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
    
    
    bool is_excp_up(int id1, int id2, int f , int l) {
        //int f = 0, l = excpID_O[id1].size() - 1;
        while(f <= l){
            int m = (f + l)/2;
            if(excpID_O[id1][m] == id2)return true;
            else if(excpID_O[id1][m] < id2)f = m + 1;
            else l = m - 1;
        }
        return false;
    }
    
    
    void cal_index_up(){
        //cout<<"topoorder size:"<<TopoOrder.size()<<endl;
        //按照拓扑顺序进行遍历
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            //u的旧id
            int u = TopoOrder[i];
            //初始的间隔域
            unsigned tmpLup=0, tmpInterval =nodes[u].oldID_mapto_newID;
            //int up=nodeLabel1[u].Level.first;
            //改点的异常点长度
            int right = excpID_O[u].size() - 1;
            //ID temp_nor = oldID_mapto_newID[u];
            //遍历u的子节点
            for(int j=0; j<nodes[u].N_O_SZ; j++){
                //u的子节点
                int addr = nodes[u].N_O[j];
                //子节点新的id
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                //如果子节点向上索引大于0
                if(nodeLabel1[addr].Level.second > tmpLup){
                    tmpLup = nodeLabel1[addr].Level.second;
                }
                
                if(nodes[addr].root_located_up == nodes[u].root_located_up) {
                    //interval
                    if((right < 0 || !is_excp(u, addr, 0, right)) && tmpInterval < nodeLabel3[addr_newID].interval)
                        tmpInterval = nodeLabel3[addr_newID].interval;
                }
            }
            //nodeLabel1[u].Level.first = tmpLup+1;
            nodeLabel2[nodeLabel1[u].oldID_mapto_newID].interval = tmpInterval;
            //nor_interval[oldID_mapto_newID[u]] = temp_nor;
        }
    }
    
    
//    void cal_index_up(){
//        //cout<<"topoorder size:"<<TopoOrder.size()<<endl;
//        //按照拓扑顺序进行遍历
//        for(int i = TopoOrder.size() - 1; i >= 0; i--){
//            //u的旧id
//            int u = TopoOrder[i];
//            //初始的间隔域
//            unsigned tmpLup=0, tmpInterval =i+1;
//            //int up=nodeLabel1[u].Level.first;
//            //改点的异常点长度
//            int right = excpID_O[u].size() - 1;
//            //遍历u的子节点
//            for(int j=0; j<nodes[u].N_O_SZ_UP; j++){
//                //u的子节点
//                int addr = nodes[u].N_O_UP[j];
//                //子节点新的id
//                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
//                if(nodes[addr].root_located_up== nodes[u].root_located_up) {
//                    //interval
//                    if((right < 0 || !is_excp_up(u, addr, 0, right)) && tmpInterval < nodeLabel3[addr_newID].interval)
//                        tmpInterval = nodeLabel3[addr_newID].interval;
//                }
//            }
//            nodeLabel3[nodeLabel1[u].oldID_mapto_newID].interval = tmpInterval;
//        }
//    }
    
    
    void cal_excp_and_cross_arr_up(){
        //reach_flag = (int *)calloc((nodes.size() + 1), sizeof(int));
        //int *exit_flag = (int *)calloc((nodes.size() + 1), sizeof(int));
        //querycnt = -1;
        //bool flag;
        for(int i = TopoOrder.size() - 1; i >= 0; i--){
            int u = TopoOrder[i];
            int u_newID = nodeLabel1[u].oldID_mapto_newID;
            
            //querycnt += 2;
            int sz = excpID_O[u].size();
            //int cnt = 0;
            for(int n = 0; n < sz; n ++){
                int newID = nodeLabel1[excpID_O[u][n]].oldID_mapto_newID;
                if(nodeLabel3[u_newID].interval < nodeLabel3[newID].interval /*&& exit_flag[newID] != querycnt*/){
                    nodeLabel3[u_newID].excpID.push_back(newID);
                    //exit_flag[newID] = querycnt;
                }
            }
            
            for(int j=0; j<nodes[u].N_O_SZ_UP; j++){
                int addr = nodes[u].N_O_UP[j];
                int addr_newID = nodeLabel1[addr].oldID_mapto_newID;
                //int len = id_cross_arr_pro[u_newID].size();
                if(nodeLabel3[addr_newID].rlt == nodeLabel3[u_newID].rlt) {
                    //excpID
                    sz = nodeLabel3[addr_newID].excpID.size();
                    for(int n = 0; n < sz; n ++){
                        if(nodeLabel3[u_newID].interval < nodeLabel3[nodeLabel3[addr_newID].excpID[n]].interval){
                            nodeLabel3[u_newID].excpID.push_back(nodeLabel3[addr_newID].excpID[n]);
                        }
                    }
                   
                }
            }
            
            if(nodeLabel3[u_newID].excpID.size() > 0)
                sort(nodeLabel3[u_newID].excpID.begin(), nodeLabel3[u_newID].excpID.end());//, cmpNewID);
        }
        
    }
    
    void index_up_constuction()
    {
        nodeLabel3 = (struct label3 *)calloc(nodes.size() + 1, sizeof(struct label3));
        temp_root_arr.clear();
        for (int u = 0; u < nodes.size(); u++) {
            //逆向分层入度为0的点
            if(nodes[u].indegree_up == 0){
                temp_root_arr.push_back(u);
            }
        }
        int *curInDegree =  (int *)calloc(nodes.size(), sizeof(int));
        int *nextInDegree = (int *)calloc(nodes.size(), sizeof(int));
        bool *cross_vertices_arr = (bool *)calloc(nodes.size(), sizeof(bool));
        oldID_mapto_newID_count = 1;
        TopoOrder.clear();
        //memset(nodes, 0, nodes.size()*(sizeof(int)))
        for(int i = 0; i < temp_root_arr.size(); i ++){
            //这里不需要建立向上向下索引
            //nodeLabel1[temp_root_arr[i]].Level.second = 1;
            
            TopoOrder.push_back(temp_root_arr[i]);
            nodes[temp_root_arr[i]].root_located_up= temp_root_arr[i] + 1;
            
            nodes[temp_root_arr[i]].root_layer_up= oldID_mapto_newID_count;
        }
        memset(cross_vertices_arr, false, sizeof(bool) * nodes.size());
        cross_id.clear();
        pre_process_up(cross_vertices_arr, curInDegree);
        while(cross_id.size() != 0){
            TopoOrder_cross.clear();
            oldID_mapto_newID_count++;
            
            for(int i = 0; i < cross_id.size(); i ++){
                if(curInDegree[cross_id[i]] == 0/* && degree[cross_id[i]].outdeg != 0*/){
                    temp_root_arr.push_back(cross_id[i]);
                    TopoOrder_cross.push_back(cross_id[i]);
                    nodes[cross_id[i]].root_located_up= cross_id[i] + 1;
                    
                    nodes[cross_id[i]].root_layer_up= oldID_mapto_newID_count;
                }
            }
            
            memset(cross_vertices_arr, false, sizeof(bool) * nodes.size());
            pre_process_cross_up(cross_vertices_arr, curInDegree, nextInDegree);
            int* temp = curInDegree;
            curInDegree = nextInDegree;
            nextInDegree = temp;
        }
        excpID_O.clear();
        decode_up(curInDegree);
        cal_index_up();
        id_cross_arr_pro.clear();
        //cal_excp_and_cross_arr_up();
    }
    
    
    void index_construction() {
        timeval start_time, end_time;
        gettimeofday(&start_time, 0);
        index_down_constuction();
        index_up_constuction();
        gettimeofday(&end_time, 0);
        printf("index time: %.3fms\n",
               (end_time.tv_sec - start_time.tv_sec) * 1000 +
               double(end_time.tv_usec - start_time.tv_usec) / 1000);
        
        cout<<"total root layer: "<<oldID_mapto_newID_count<<endl;
        //space
        int totalsize = 6 * nodes.size();
        for(int i = 1; i <= nodes.size(); i ++){
            totalsize += nodeLabel2[i].excpID.size() + nodeLabel2[i].id_cross_arr.size();//+nodeLabel3[i].excpID.size();
        }
        totalsize +=  oldID_mapto_newID_count + temp_root_arr.size();
        cout<<"index size (MB): "<<(double)totalsize*4/(1024*1024)<<endl;
        cout<<"index size (KB): "<<(double)totalsize*4/1024<<endl;
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
    
    
    bool same_layer_tree_reach_up_1(int id1, int id2){
        //reach_flag[id1] = querycnt;
        if(nodeLabel3[id1].rlt != nodeLabel3[id2].rlt){
            return false;
        }
        else{
            if(nodeLabel3[id1].interval <= nodeLabel3[id2].interval){
                return true;
            }
            else {
                int len = nodeLabel3[id2].excpID.size();
                for(int i = 0; i < len; i ++) {
//                    if(nodeLabel3[id2].excpID[i] < id1)
//                        return false;
                    if(nodeLabel3[nodeLabel3[id2].excpID[i]].interval >= nodeLabel3[id1].interval) {
                        return true;
                    }
                } //for
                
                return false;
            }
        }
    }
    
    bool down_layer_to_up_layer_without_cross_located1(int id1, int id2){
        //reach_flag[id1] = querycnt;
        int len = nodeLabel2[id1].id_cross_arr.size();
        
        for(int i = 0; i < len; i ++){
            if(nodeLabel2[id1].id_cross_arr[i] > id2)
                return false;
            //else if(reach_flag[nodeLabel2[id1].id_cross_arr[i]] == querycnt)continue;
            else{
                if(nodeLabel3[id1].rl==nodeLabel3[id2].rl)
                {
                    //count++;
                    return same_layer_tree_reach_up_1(id1, id2);
                }
                //reach_flag[nodeLabel2[id1].id_cross_arr[i]] = querycnt;
                if(nodeLabel2[nodeLabel2[id1].id_cross_arr[i]].rl < nodeLabel2[id2].rl){
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
        if(nodeLabel3[id1].rl==nodeLabel3[id2].rl)
        {
            return same_layer_tree_reach_up_1(id1, id2);
        }
        if(nodeLabel2[id1].rl < nodeLabel2[id2].rl){
            return down_layer_to_up_layer_without_cross_located1(id1, id2);
        }
        else {
            return same_layer_tree_reach1(id1, id2);
        }
        return false;
    }
    
    bool same_layer_tree_reach(int id1, int id2){

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
    
    
    bool same_layer_tree_reach_up(int id1, int id2){
        //reach_flag[id1] = querycnt;
        if(nodeLabel3[id1].rlt != nodeLabel3[id2].rlt){
            return false;
        }
        else{
            if(nodeLabel3[id1].interval <= nodeLabel3[id2].interval){
                return true;
            }
            else {
                int len = nodeLabel3[id2].excpID.size();
                for(int i = 0; i < len; i ++) {
//                    if(nodeLabel3[id2].excpID[i] < id1)
//                        return false;
                    if(nodeLabel3[nodeLabel3[id2].excpID[i]].interval >= nodeLabel3[id1].interval) {
                        return true;
                    }
                } //for
                
                return false;
            }
        }
    }
    
    bool down_layer_to_up_layer_without_cross_located(int id1, int id2){
        //reach_flag[id1] = querycnt;
        int len = nodeLabel2[id1].id_cross_arr.size();
        
        for(int i = 0; i < len; i ++){
            if(nodeLabel2[id1].id_cross_arr[i] > id2)
                return false;
            //    if(nodeLabel2[id1].id_cross_arr[i] == id2)
            //        return true;
            //else if(reach_flag[nodeLabel2[id1].id_cross_arr[i]] == querycnt)continue;
            else{
                
                if(nodeLabel3[id1].rl==nodeLabel3[id2].rl)
                {
                    count++;
                    return same_layer_tree_reach_up(id1, id2);
                }
                //reach_flag[nodeLabel2[id1].id_cross_arr[i]] = querycnt;
                if(nodeLabel2[nodeLabel2[id1].id_cross_arr[i]].rl < nodeLabel2[id2].rl){
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
        if(nodeLabel3[id1].rl==nodeLabel3[id2].rl)
        {
            count++;
            return same_layer_tree_reach_up(id1, id2);
        }
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
            //ID id1 = *x;
            //ID id2 = *y;
            if(*x == *y)
                r = true;
            else if(nodeLabel1[*x].Level.second >= nodeLabel1[*y].Level.second ||
                    nodeLabel1[*x].Level.first <= nodeLabel1[*y].Level.first)
                r = false;
            else{
                //querycnt ++;
                if(nodeLabel1[*x].oldID_mapto_newID > nodeLabel1[*y].oldID_mapto_newID)
                    r = false;
                else r = query(nodeLabel1[*x].oldID_mapto_newID, nodeLabel1[*y].oldID_mapto_newID);
            }
        }
        gettimeofday(&end_at, 0);
        cout<<count<<endl;
        printf("*****query time******: %.3fms\n",
               (end_at.tv_sec - start_at.tv_sec) * 1000 +
               double(end_at.tv_usec - start_at.tv_usec) / 1000);
        
        int reachable = 0, nonreachable =0;
        int falsepositive = 0, falsenegative = 0;
        gettimeofday(&start_at, 0);
        for(vector<int>::iterator x=src.begin(), y=dest.begin(); x!=src.end(); x++, y++) {
            if(*x == *y)
                r = true;
            else if(nodeLabel1[*x].Level.second >= nodeLabel1[*y].Level.second ||
                    nodeLabel1[*x].Level.first <= nodeLabel1[*y].Level.first)
                r = false;
            else{
                //querycnt ++;
                if(nodeLabel1[*x].oldID_mapto_newID > nodeLabel1[*y].oldID_mapto_newID)
                    r = false;
                else r = query1(nodeLabel1[*x].oldID_mapto_newID, nodeLabel1[*y].oldID_mapto_newID);
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
    
    //read_graph("/Users/youyujie/Documents/graph");
    read_graph(argv[1]);
    
    index_construction();
    
    //read_queries("/Users/youyujie/Documents/query");
    read_queries(argv[2]);
    
    run_queries();
    
    return 0;
}
