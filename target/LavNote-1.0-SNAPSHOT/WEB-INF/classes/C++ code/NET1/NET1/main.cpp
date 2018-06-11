//
//  main.cpp
//  NET1
//
//  Created by 游宇杰 on 2018/3/27.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include<iostream>
#include<vector>
#include<cstring>
#define N 20000
using namespace std;
bool is_a_solution(int a[],int yu,int k)
{
    if(k<2) return false;
    if(a[1]%a[2]>=yu) return true;
    return false;
}

void construct_candidates(bool a[],int k,vector<int> input,int *ncandidates,int c[])
{
    int count=0;
    int j=0;
    for(int i=0;i<input.size();i++)
    {
        if(a[i])
        {
            count=i;
            j++;
            break;
        }
    }
    if(j!=0) count=count+1;
    j=0;
    for (; count<input.size(); count++) {
        c[j]=count;
        j++;
    }
    *ncandidates=j;
}

void backtrack(bool a[],int k,vector<int> input,int yu,int *count,int t[])
{
    int c[N];
    int ncandidates;
    //int i;
    if(is_a_solution(t,yu,k))
    {
        (*count)++;
    }
    else{
        k=k+1;
        construct_candidates(a,k,input,&ncandidates,c);
        for (int i=0; i<ncandidates; i++) {
            t[k]=input[c[i]];
            a[c[i]]=true;
            backtrack(a,k,input,yu,count,t);
            a[c[i]]=false;
        }
    }
}
int main(int argc, const char * argv[]) {
    int x;
    int y;
    x=5;
    y=2;
    //cin>>x;
    //cin>>y;
    bool a[N];
    memset(a,false,sizeof(a));
    int k=0;
    vector<int> input;
    //input.resize(x);
    for(int i=0;i<x;i++)
    {
        input.push_back(i);
    }
    int count=0;
    int t[3];
    backtrack(a, k, input, y, &count, t);
    cout<<count<<endl;
    return 0;
}
