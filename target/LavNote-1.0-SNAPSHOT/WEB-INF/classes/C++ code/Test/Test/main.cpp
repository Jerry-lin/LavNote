//
//  main.cpp
//  Test
//
//  Created by 游宇杰 on 2018/3/30.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
#define N 100
bool is_a_solution(int len,int k);
void process_solution(vector<int> &t,vector<int> &result);
void construct_candidate(int start,vector<int> &t,int candidates[],int *ncaididate,vector<int> &data);
void backtrack(int len,vector<int> &t,vector<int> &data,vector<int> &result,int start);
int main(int argc, const char * argv[]) {
    int end=5;
    vector<int> data{2,0,1,1,1};
    vector<int> t;
    vector<int> result;
    int start=0;
    backtrack(end, t, data, result, start);
    sort(result.begin(),result.end());
    if(result.size()==0)
     cout<<-1<<endl;
    else
     cout<<result[0]<<endl;
    return 0;
}
bool is_a_solution(int end,int k)
{
    return k==end-1;
}
void process_solution(vector<int> &t,vector<int> &result)
{
    result.push_back(t.size());
    t.clear();
}
void construct_candidate(int start,vector<int> &t,int candidates[],int *ncaididate,vector<int> &data)
{
    //当前所处的位置
    int x=t.size()-1;
    int now_position=0;
    if(x>=0) now_position=t[x];
    //else t.push_back(0);
    //当前可以走的步数
    int next_step=data[now_position];
    for (int i=1; i<=next_step; i++) {
        //接下来可能到的位置
        int next_position=i+now_position;
        //这里进行剪枝
        if(next_position<=data.size()&&data[next_position]!=0)
        {
             candidates[*ncaididate]=next_position;
            (*ncaididate)++;
        }
    }
}
void backtrack(int end,vector<int> &t,vector<int> &data,vector<int> &result,int start)
{
    int ncandidate=0;
    int candidates[N];
    if(is_a_solution(end,start))
    {
        process_solution(t,result);
    }
    else{
        start++;
        construct_candidate(start,t,candidates,&ncandidate,data);
        for (int i=0; i<ncandidate; i++) {
            int next_step=candidates[i];
            t.push_back(next_step);
            backtrack(end, t,data,result, start);
            t.pop_back();
        }
    }
}
