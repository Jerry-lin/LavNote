//
//  main.cpp
//  MyMGTag
//
//  Created by 游宇杰 on 2018/6/4.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
#include <vector>
#include <sys/time.h>
#include <queue>
#include <climits>
namespace mg {
using namespace std;
//保存访问的节点
struct Node{
    //这个节点所在子图的根
    int root;
    //孩子节点
    vector<Node*> kids;
    //上一次访问该点的副节点是否是根节点
    bool visit_Is_Root;
    //节点的id，可以定位节点在vector中的位置，主要用来定位
    int val;
    //如果父节点有两个或者两个以上是根节点，那么这个节点变成新的根节点
    bool flag;
    Node(int val)
    {
        this->val=val;
        this->visit_Is_Root=false;
        this->flag=false;
    };
};

struct label1{
    
    /*
     second表示向下索引
     first表示向上索引
     */
    pair<int, int> Level;
};
    
struct label2{
    //节点所在层次
    int rl;
    //节点所在子图的根
    int root;
    //图内间隔阈
    int interval;
    //异常点
    vector<int> excption;
    //跨子图点
    vector<int> cross;
};

//保存输入的节点
vector<Node*> nodes;
//每个节点的入度
vector<int> indegrees;
//子图之间的索引
label1 *nodeLabel1;
//图内索引
label2 *nodeLabel2;
//用来判断是否被访问过
vector<bool> is_visited;
    
void read_graph(const char *filename) {
        timeval start_at, end_at;
        //开始时间
        gettimeofday(&start_at, 0);
        //以读的方式打开文件
        FILE *file = fopen(filename, "r");
        //文件是以该字符串开始的
        char header[] = "graph_for_greach";
        fscanf(file, "%s", header);
        int n;
        //总的节点数
        fscanf(file, "%d", &n);
        //初始化nodes和indegrees容量
        nodes.resize(n);
        indegrees.resize(n);
        is_visited.resize(n);
        nodeLabel1 = (struct label1 *)calloc(n, sizeof(struct label1));
        nodeLabel2 = (struct label2 *)calloc(n, sizeof(struct label2));
        for (;;) {
            //u存储当前节点，v存储u的子结点
            int u, v;
            //到文件的结尾，结束读入操作
            if (feof(file) || fscanf(file, "%d", &u) != 1) {
                break;
            }
            fgetc(file);
            if(nodes[u]==NULL)
            {
                //初始化根节点
                nodes[u]=new Node(u);
            }
            while (!feof(file) && fscanf(file, "%d", &v) == 1) {
                if(nodes[v]==NULL)
                {
                    //初始化子节点
                    nodes[v]=new Node(v);
                }
                //父节点内部孩子指针指向子节点
                nodes[u]->kids.push_back(nodes[v]);
                //子节点的入度加1
                indegrees[v]++;
            }
            fgetc(file);
        }
        fclose(file);
    
        //获取结束时间
        gettimeofday(&end_at, 0);
        //打印出读图的耗时
        printf("read time(graph): %.3fs\n",
               end_at.tv_sec - start_at.tv_sec +
               double(end_at.tv_usec - start_at.tv_usec) / 1000000);
}

//根据论文来划分图
void partition_Graph(vector<Node*> &nodes,vector<int> &indegrees)
{
    queue<Node*> tmp;
    for(int i=0;i<indegrees.size();i++)
    {
        if(indegrees[i]==0&&!is_visited[i])
        {
            tmp.push(nodes[i]);
            //确定向下索引和根节点，以及层次
            //向下索引是1
            nodeLabel1[i].Level.second=1;
            //向上索引设置为整型最大值
            nodeLabel1[i].Level.first=INT_MAX;
            //根节点是自己
            nodeLabel2[i].root=i;
            //层次开始是1
            nodeLabel2[i].rl=1;
            //表示访问过
            is_visited[i]=true;
            while(tmp.size()!=0)
            {
                //从队列中取出需要遍历的节点
                Node* root=tmp.front();
                //根节点的位置
                int rootPos=root->val;
                
                //取出自己节点进行深度遍历
                for(int j=0;j<root->kids.size();j++)
                {
                    //定位到具体的位置
                    int pos=root->kids[j]->val;
                    //子节点的入度减一
                    indegrees[pos]--;
                    //表示访问过
                    is_visited[pos]=true;
                    //如果入度不为0，那么就改变root，向下索引，前一个节点是否是根节点
                    if(indegrees[pos]!=0)
                    {
                        //向下索引取父节点最大
                        /*
                        nodeLabel1[pos].Level.second=nodeLabel1[rootPos].Level.second+1>nodeLabel1[pos].Level.second?(nodeLabel1[rootPos].Level.second+1):nodeLabel1[pos].Level.second;*/
                        nodeLabel2[pos].rl=nodeLabel2[rootPos].rl>nodeLabel2[pos].rl?(nodeLabel2[rootPos].rl):nodeLabel2[pos].rl;
                            //父节点是根节点，那么将子节点的bool变为true
                            if(rootPos==nodeLabel2[rootPos].root)
                            {
                                //第一次有一个父节点为根
                                if(!nodes[pos]->visit_Is_Root){
                                //将第一次为根的父节点保存起来
                                 nodes[pos]->visit_Is_Root=true;
                                    nodeLabel2[pos].root=rootPos;
                                }
                               else
                                   nodes[pos]->flag=true;//多次父节点为根
                            }
                      
                        //如果父节点都不是根节点，那么子节点将会和父节点在一个子图内
                    }else if(!nodes[pos]->visit_Is_Root&&indegrees[pos]==0)
                    {
                        //子节点的根节点和父节点一样
                        nodeLabel2[pos].root=nodeLabel2[rootPos].root;
                        nodeLabel2[pos].rl=nodeLabel2[rootPos].rl;
                        
                        //子节点的向下索引取父节点最大
                        /*
                        if(nodeLabel1[rootPos].Level.second+1>nodeLabel1[pos].Level.second)
                        {
                            nodeLabel1[pos].Level.second=nodeLabel1[rootPos].Level.second+1;
                        }*/
                        //将入度为0的点放入队列
                        tmp.push(nodes[pos]);
                        //如果父节点有根节点，那么进一步判断
                    }else if(nodes[pos]->visit_Is_Root&&indegrees[pos]==0)
                    {
                        //如果有两个和两个以上的根节点，称为一个新的根节点
                        if(nodeLabel2[pos].root!=nodeLabel2[rootPos].root||nodes[pos]->flag)
                        {
                            nodeLabel2[pos].root=pos;
                            //判断比较大的层次加1
                            nodeLabel2[pos].rl=1+(nodeLabel2[rootPos].rl>nodeLabel2[pos].rl?nodeLabel2[rootPos].rl:nodeLabel2[pos].rl);
                        }
                        else{
                            nodeLabel2[pos].rl=nodeLabel2[rootPos].rl;
                        }
                        //将入度为0的点放入队列
                        tmp.push(nodes[pos]);
                    }
                    //这里是代码优化，主要以计算向下索引
                nodeLabel1[pos].Level.second=nodeLabel1[rootPos].Level.second+1>nodeLabel1[pos].Level.second?(nodeLabel1[rootPos].Level.second+1):nodeLabel1[pos].Level.second;
                    //计算向上索引
                    if(nodeLabel1[pos].Level.first==0)
                    {
                        nodeLabel1[pos].Level.first=nodeLabel1[rootPos].Level.first-1;
                    }
                    else{
                        nodeLabel1[pos].Level.first=(nodeLabel1[rootPos].Level.first-1)>nodeLabel1[pos].Level.first?nodeLabel1[pos].Level.first:(nodeLabel1[rootPos].Level.first-1);
                    }
                }
                //将根点从队列中移除
                tmp.pop();
            }
        }
    }
}

void construct_index()
{
    partition_Graph(nodes,indegrees);
}
}

int main(int argc, const char * argv[]) {
    using namespace mg;
    //读图
    read_graph("/Users/youyujie/Downloads/graph_for_greach");
    construct_index();
    for(int i=0;i<19;i++)
    {
        cout<<i<<":"<<nodeLabel2[i].root<<":"<<nodeLabel2[i].rl<<":"<<nodeLabel1[i].Level.second<<endl;
    }
    
    return 0;
}
