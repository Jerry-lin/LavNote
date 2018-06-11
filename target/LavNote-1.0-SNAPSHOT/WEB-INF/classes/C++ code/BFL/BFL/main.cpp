#include <algorithm>
#include <sys/time.h>
#include<stdio.h>
#include<iostream>
#include<iterator>
#include<vector>

using namespace std;

#ifndef K
#define K 5
#endif
#ifndef D
#define D (320 * K)
#endif

namespace bs {
    
    using namespace std;
    
    struct node {
        //N_I表示能到达该点的一跳点，N_O表示该点能到达的一跳点
        vector<int> N_I;
        vector<int> N_O;
        int vis;
        union {
            
            //对应哈希函数哈希的值数组
            int L_in[K];
#if K > 8
            unsigned int h_in;
#else
            unsigned char h_in;
#endif
        };
        union {
            int L_out[K];
#if K > 8
            unsigned int h_out;
#else
            unsigned char h_out;
#endif
        };
        //first表示发现时间，second表示完成时间
        pair<int, int> L_interval;
    };
    vector<node> nodes;
    int vis_cur, cur, traverseCnt;
    
    void read_graph(const char *filename) {
        timeval start_at, end_at;
        gettimeofday(&start_at, 0);
        FILE *file = fopen(filename, "r");
        char header[] = "graph_for_greach";
        fscanf(file, "%s", header);
        int n;
        fscanf(file, "%d", &n);
        nodes.resize(n);
        for (;;) {
            int u, v;
            if (feof(file) || fscanf(file, "%d", &u) != 1) {
                break;
            }
            fgetc(file);
            while (!feof(file) && fscanf(file, "%d", &v) == 1) {
                nodes[u].N_O.push_back(v);
                nodes[v].N_I.push_back(u);
            }
            fgetc(file);
        }
        fclose(file);
        gettimeofday(&end_at, 0);
        printf("read time(graph): %.3fs\n",
               end_at.tv_sec - start_at.tv_sec
               + double(end_at.tv_usec - start_at.tv_usec) / 1000000);
    }
    
    //相当于论文中提到的g()函数，用于合并顶点
    int h_in() {
        static int c = 0, r = rand();
        //每nodes.size()/D次产生新的随机数
        if (c >= (int) nodes.size() / D) {
            c = 0;
            r = rand();
        }
        c++;
        return r;
    }
    
    //相当于论文中提到的g()函数，用于合并顶点
    int h_out() {
        static int c = 0, r = rand();
        if (c >= (int) nodes.size() / D) {
            c = 0;
            r = rand();
        }
        c++;
        return r;
    }
    
    void dfs_in(node &u) {
        u.vis = vis_cur;
        //入度为0的点
        if (u.N_I.empty()) {
            //相当于h(g()),对顶点进行hash
            u.h_in = h_in() % (K * 32);
        } else {
            for (int i = 0; i < K; i++) {
                u.L_in[i] = 0;
            }
            
            for (int i = 0; i < u.N_I.size(); i++) {
                node &v = nodes[u.N_I[i]];
                //判断是否计算过
                if(v.vis != vis_cur) {
                    dfs_in(v);
                }
                //v为入读为0的点
                if (v.N_I.empty()) {
                    int hu = v.h_in;
                    //截取hu后5位，确定前四位后，将最后一位与数组相与复制给数组
                    u.L_in[(hu >> 5) % K] |= 1 << (hu & 31);
                } else {
                    //父节点与子节点相或
                    for (int j = 0; j < K; j++) {
                        u.L_in[j] |= v.L_in[j];
                    }
                }
            }
            
            int hu = h_in();
            u.L_in[(hu >> 5) % K] |= 1 << (hu & 31);
        }
    }
    
    void dfs_out(node &u) {
        u.vis = vis_cur;
        //越接近根，该值越小
        u.L_interval.first = cur++;
        
        if (u.N_O.empty()) {
            u.h_out = h_out() % (K * 32);
        } else {
            for (int i = 0; i < K; i++) {
                u.L_out[i] = 0;
            }
            
            for (int i = 0; i < u.N_O.size(); i++) {
                node &v = nodes[u.N_O[i]];
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
        //越接近根，该值越大
        u.L_interval.second = cur;
    }
    
    void index_construction() {
        timeval start_at, end_at;
        gettimeofday(&start_at, 0);
        
        vis_cur++;
        for (int u = 0; u < nodes.size(); u++) {
            //出度为0的点
            if (nodes[u].N_O.size() == 0) {
                //从下至上深度遍历
                dfs_in(nodes[u]);
            }
        }
        vis_cur++;
        cur = 0;
        for (int u = 0; u < nodes.size(); u++) {
            //入度为0的点
            if (nodes[u].N_I.size() == 0) {
                //从上至下深度遍历
                dfs_out(nodes[u]);
            }
        }
        
        gettimeofday(&end_at, 0);
        printf("index time: %.3fs\n",
               end_at.tv_sec - start_at.tv_sec
               + double(end_at.tv_usec - start_at.tv_usec) / 1000000);
        long long index_size = 0;
        for (int u = 0; u < nodes.size(); u++) {
            index_size +=
            nodes[u].N_I.empty() ?
            sizeof(nodes[u].h_in) : sizeof(nodes[u].L_in);
            index_size +=
            nodes[u].N_O.empty() ?
            sizeof(nodes[u].h_out) : sizeof(nodes[u].L_out);
            index_size += sizeof(nodes[u].L_interval);
        }
        printf("index space: %.3fMB\n", double(index_size) / (1024 * 1024));
    }
    
    vector<pair<pair<node, node>, int> > queries;
    
    void read_queries(const char *filename) {
        timeval start_at, end_at;
        gettimeofday(&start_at, 0);
        FILE *file = fopen(filename, "r");
        int u, v, r;
        while (fscanf(file, "%d%d%d", &u, &v, &r) == 3) {
            //构建查询的pair
            queries.push_back(make_pair(make_pair(nodes[u], nodes[v]), r));
        }
        fclose(file);
        gettimeofday(&end_at, 0);
        printf("read time(query): %.3fs\n",
               end_at.tv_sec - start_at.tv_sec
               + double(end_at.tv_usec - start_at.tv_usec) / 1000000);
    }
    
    bool reach(node &u, node &v) {
        //利用标签判断
        if (u.L_interval.second < v.L_interval.second) {
            return false;
        } else if (u.L_interval.first <= v.L_interval.first) {
            return true;
        }
        //如果v是入度为零的点，则一定不可达
        if (v.N_I.empty()) {
            return false;
        }
        //如果v是出度为零的点，则一定不可达
        if (u.N_O.empty()) {
            return false;
        }
        //如果v是出度为零的点
        if (v.N_O.empty()) {
            if ((u.L_out[v.h_out >> 5] & (1 << (v.h_out & 31))) == 0) {
                return false;
            }
        } else {
            //u的out集合和v的out集合进行与计算，如果与v的out集合不匹配，那么不可达
            for (int i = 0; i < K; i++) {
                if ((u.L_out[i] & v.L_out[i]) != v.L_out[i]) {
                    return false;
                }
            }
        }
        //如果u是入度为零的点
        if (u.N_I.empty()) {
            if ((v.L_in[u.h_in >> 5] & (1 << (u.h_in & 31))) == 0) {
                return false;
            }
        } else {
            //u的in集合和v的in集合进行与计算，如果与u的in集合匹配，那么不可达
            for (int i = 0; i < K; i++) {
                if ((u.L_in[i] & v.L_in[i]) != u.L_in[i]) {
                    return false;
                }
            }
        }
        
        for (vector<int>::iterator it = u.N_O.begin(); it != u.N_O.end(); it++) {
            if (nodes[*it].vis != vis_cur) {
                nodes[*it].vis = vis_cur;
                traverseCnt ++;
                if (reach(nodes[*it], v)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    void run_queries() {
        timeval start_at, end_at;
        traverseCnt = 0;
        //printf("%d\n", traverseCnt);
        gettimeofday(&start_at, 0);
        for (vector<pair<pair<node, node>, int>>::iterator it = queries.begin();
             it != queries.end(); it++) {
            vis_cur++;
            //使用reach函授检索是否可达
            int result = reach(it->first.first, it->first.second);
         
        }
        gettimeofday(&end_at, 0);
        printf("query time: %.3fms\n",
               (end_at.tv_sec - start_at.tv_sec) * 1000
               + double(end_at.tv_usec - start_at.tv_usec) / 1000);
        
        //printf("num: %d traverse num: %d average: %f\n", node.size(), traverseCnt, (double)traverseCnt/node.size());
        std::cout<<"num: "<<nodes.size()<<" traverse num: "<<traverseCnt<<" average: "<<(double)traverseCnt/(nodes.size())<<std::endl;
        
        int count = 0;
        gettimeofday(&start_at, 0);
        for (vector<pair<pair<node, node>, int>>::iterator it = queries.begin();
             it != queries.end(); it++) {
            vis_cur++;
            int result = reach(it->first.first, it->first.second);
            it->second = result;
            //if (it->second == -1 || it->second == result) {
            //        it->second = result;
            //} else {
            //fprintf(stderr, "ERROR!\n");
            //exit(EXIT_FAILURE);
            //}
            if(result == 1){
                count ++;
            }
        }
        gettimeofday(&end_at, 0);
        printf("query time: %.3fms\n",
               (end_at.tv_sec - start_at.tv_sec) * 1000
               + double(end_at.tv_usec - start_at.tv_usec) / 1000);
        cout<<"reachable: "<<count<<endl;
    }
    
    void write_results() {
        int ncut = 0, pcut = 0, pos = 0;
        for (int i = 0; i < queries.size(); i++) {
            node& u = queries[i].first.first, & v = queries[i].first.second;
            bool pf = false, nf = false;
            
            if (u.L_interval.second < v.L_interval.second) {
                nf = true;
            } else if (u.L_interval.first <= v.L_interval.first) {
                pf = true;
            }
            
            if (v.N_I.empty()) {
                nf = true;
            }
            if (u.N_O.empty()) {
                nf = true;
            }
            if (v.N_O.empty()) {
                if ((u.L_out[v.h_out >> 5] & (1 << (v.h_out & 31))) == 0) {
                    nf = true;
                }
            } else {
                for (int i = 0; i < K; i++) {
                    if ((u.L_out[i] & v.L_out[i]) != v.L_out[i]) {
                        nf = true;
                    }
                }
            }
            if (u.N_I.empty()) {
                if ((v.L_in[u.h_in >> 5] & (1 << (u.h_in & 31))) == 0) {
                    nf = true;
                }
            } else {
                for (int i = 0; i < K; i++) {
                    if ((u.L_in[i] & v.L_in[i]) != u.L_in[i]) {
                        nf = true;
                    }
                }
            }
            
            if (queries[i].second) {
                pos++;
            }
            if (nf) {
                ncut++;
            }
            if (pf) {
                pcut++;
            }
        }
        printf("reachable: %d\n", pos);
        printf("answered only by label: %d + %d = %d\n", ncut, pcut, ncut + pcut);
    }
    
}

int main(int argc, char *argv[]) {
    using namespace bs;
    //读图
    read_graph("/Users/youyujie/Documents/graph");
    //构建索引
    index_construction();
    //读图
    read_queries("/Users/youyujie/Documents/query");
    //运行查询
    run_queries();
    //回写结果
    write_results();
    
    return 0;
}

