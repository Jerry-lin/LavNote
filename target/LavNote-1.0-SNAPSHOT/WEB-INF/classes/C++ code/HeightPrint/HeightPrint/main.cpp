//
//  main.cpp
//  HeightPrint
//
//  Created by 游宇杰 on 2018/4/30.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
#include <vector>
#include <map>
using namespace std;
struct TreeNode
{
    int value;
    TreeNode* leftChild=NULL;
    TreeNode* rightChild=NULL;
};
void printNode(TreeNode *root)
{
    vector<TreeNode*> result;
    if(root==NULL) return;
    result.push_back(root);
    cout<<root->value<<" ";
    int i=0;
    int count=1;
    while(i<count)
    {
        if(result[i]->leftChild!=NULL)
        {
            result.push_back(result[i]->leftChild);
            cout<<result[i]->leftChild->value<<" ";
            count++;
        }
        if(result[i]->rightChild!=NULL)
        {
            result.push_back(result[i]->rightChild);
            cout<<result[i]->rightChild->value<<" ";
            count++;
        }
        i++;
    }
}

int getFirstIndex(vector<int> data,int low,int high,int k)
{
    int index=0;
    while(low<=high)
    {
        int mid=(low+high)/2;
        if(data[mid]==k)
        {
            if(mid==0||(mid>0&&data[mid-1]<k))
            {
                index=mid;
                return index;
            }
            else high=mid-1;
        }else if(data[mid]>k){
            high=mid-1;
        }
        else
        {
            low=mid+1;
        }
    }
    return -1;
}
int getLastIndex(vector<int> data,int low,int high,int k)
{
    int index=0;
    while(low<=high)
    {
        int mid=(low+high)/2;
        if(data[mid]==k)
        {
            if(mid==high||(mid<high&&data[mid+1]>k))
            {
                index=mid;
                return index;
            }
            else low=mid+1;
        }else if(data[mid]>k){
            high=mid-1;
        }
        else
        {
            low=mid+1;
        }
    }
    return -1;
}
int GetNumberOfK(vector<int> data ,int k) {
    if(data.size()==0) return 0;
    int first=getFirstIndex(data,0,data.size()-1,k);
    int second=getLastIndex(data,0,data.size()-1,k);
    if(first==-1) return 0;
    return (second-first+1);
}

int FirstNotRepeatingChar(string str) {
    if(str.size()==0) return 0;
    map<char,int> m1;
    for(int i=0;i<str.size();i++)
    {
        if(m1.find(str[i])==m1.end()) m1.insert({str[i],1});
        else{
            m1[str[i]]+=1;
        }
    }
    for(int i=0;i<m1.size();i++)
    {
        if(m1[i]==1)
            return i;
    }
    return 0;
}
int main(int argc, const char * argv[]) {
    vector<int> data{1,2,3,3,3,3,4,5};
    int t=FirstNotRepeatingChar("google");
    return 0;
}
