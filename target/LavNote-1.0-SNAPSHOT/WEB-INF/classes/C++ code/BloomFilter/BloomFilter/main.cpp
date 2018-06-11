//
//  main.cpp
//  BloomFilter
//
//  Created by 游宇杰 on 2018/4/2.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

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
#include <ctime>

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
    
    
    vector<node> nodes;
    struct label1 *nodeLabel1;
    struct label2 *nodeLabel2;
    
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
                //N_I[v].push_back(u);
                
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
    
    int h_out()
    {
        srand(time(nullptr));
        int r=rand()%nodes.size();
        return r;
    }
}
