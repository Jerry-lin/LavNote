//
//  main.cpp
//  CinTest
//
//  Created by 游宇杰 on 2018/4/28.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
#include<string>
#include<vector>
using namespace std;
struct TreeNode{
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int val)
    {
        this->val=val;
        this->left=this->right=NULL;
    }
};

void insertSort(vector<int> &nums)
{
    if(nums.size()==0) return;
    for(int i=0;i<nums.size();i++)
    {
        int j=i;
        while(j>0&&nums[j]<nums[j-1])
        {
            int tmp=nums[j-1];
            nums[j-1]=nums[j];
            nums[j]=tmp;
            j--;
        }
    }
}


void selectSort(vector<int> &nums)
{
    if(nums.size()==0) return;
    int min;
    for(int i=0;i<nums.size();i++)
    {
        min=i;
        for(int j=i+1;j<nums.size();j++)
        {
            if(nums[j]<nums[min])
                min=j;
        }
        int tmp=nums[min];
        nums[min]=nums[i];
        nums[i]=tmp;
    }
}


void dubbleSort(vector<int> &nums)
{
    if(nums.size()==0) return;
    int tmp=0;
    for(int i=0;i<nums.size();i++)
    {
        for(int j=i;j>0;j--)
        {
            if(nums[j]<nums[j-1])
            {
                tmp=nums[j-1];
                nums[j-1]=nums[j];
                nums[j]=tmp;
            }
        }
    }
    
}

void quickSort(vector<int> &nums,int start,int end)
{
    if(nums.size()==0) return;
    if(start>=end) return;
    int low=start;
    int high=end;
    int t=nums[low];
    while(low<high)
    {
        while(low<high&&nums[high]>=t)
        {
            high--;
        }
        nums[low]=nums[high];
        while(low<high&&nums[low]<=t)
        {
            low++;
        }
        nums[high]=nums[low];
    }
    nums[low]=t;
    quickSort(nums,start,low-1);
    quickSort(nums,low+1,end);
}

void mergeSort(vector<int> &nums,int tmp[],int left,int right)
{
    int mid=(left+right)/2;
    //如果数组只有一个元素，那么就结束分割
    if(left==right) return;
    //一步一步划分数组，直到只有一个元素
    mergeSort(nums,tmp,left,mid);
    mergeSort(nums,tmp,mid+1,right);
    //将子数组拷贝到临时数组
    for(int i=left;i<=right;i++)
    {
        tmp[i]=nums[i];
    }
    int i1=left,i2=mid+1;
    for(int i=left;i<=right;i++)
    {
        //左边的数组耗尽
        if(i1==mid+1)
        {
            nums[i]=tmp[i2++];
        }else if(i2>right) //右边的数组耗尽
        {
            nums[i]=tmp[i1++];
        }
        else if(tmp[i1]>tmp[i2])
        {
            nums[i]=tmp[i2++];
        }
        else
        {
            nums[i]=tmp[i1++];
        }
    }
}


bool isBalancedHelper(TreeNode *root,int *depth)
{
    if(root==NULL)
    {
        *depth=0;
        return true;
    }
    int left,right;
    if(isBalancedHelper(root->left,&left)&&isBalancedHelper(root->right,&right))
    {
        int diff=left-right;
        if(diff<=1&&diff>=-1)
        {
            *depth=1+(left>=right?left:right);
            return true;
        }
    }
    return false;
}

bool isBalanced(TreeNode * root) {
    // write your code here
    int depth;
    return isBalancedHelper(root,&depth);
}


int medianHelper(vector<int> &nums,int low,int high)
{
    int first=low;
    int second=high;
    int target=nums[first];
    while(first<second)
    {
        while(first<second&&nums[second]>=target)
        {
            second--;
        }
        nums[first]=nums[second];
        while(first<second&&nums[first]<=target)
        {
            first++;
        }
        nums[second]=nums[first];
    }
    nums[first]=target;
    int len=nums.size()%2==0?(nums.size()/2-1):nums.size()/2;
    if(first==len) return nums[first];
    else if(first>len) return medianHelper(nums,low,first-1);
    else return medianHelper(nums,first+1,high);
}
int median(vector<int> &nums) {
    int low=0;
    int high=nums.size()-1;
    int target=nums[low];
    while(low<high)
    {
        while(low<high&&nums[high]>=target)
        {
            high--;
        }
        nums[low]=nums[high];
        while(low<high&&nums[low]<=target)
        {
            low++;
        }
        nums[high]=nums[low];
    }
    nums[low]=target;
    int len=nums.size()%2==0?(nums.size()/2-1):nums.size()/2;
    if(low==len) return nums[low];
    else if(low>len) return medianHelper(nums,0,low-1);
    else return medianHelper(nums,low+1,nums.size()-1);
}


void helper(TreeNode *root,vector<string> &result,vector<int> &path,char c[])
{
    path.push_back(root->val);
    if(root->left==NULL&&root->right==NULL)
    {
        string s="";
        for(int i=0;i<path.size();i++)
        {
            sprintf(c,"%d",path[i]);
            s+=c;
            if(i!=path.size()-1)
            {
                s.push_back('-');
                s.push_back('>');
            }
        }
        result.push_back(s);
        path.pop_back();
        return;
    }
    if(root->left!=NULL)
        helper(root->left,result,path,c);
    if(root->right!=NULL)
        helper(root->right,result,path,c);
    path.pop_back();
}
vector<string> binaryTreePaths(TreeNode * root) {
    // write your code haere
    vector<string> result;
    if(root==NULL) return result;
    vector<int> path;
    char c[1000];
    helper(root,result,path,c);
    return result;
}

int main(int argc, const char * argv[]) {
    TreeNode* root=new TreeNode(100);
    root->left=new TreeNode(2);
    root->right=new TreeNode(3);
    root->left->right=new TreeNode(5);
    vector<string> result=binaryTreePaths(root);
    for(int i=0;i<result.size();i++)
    {
        cout<<result[i]<<endl;
    }
    //string s="1"+10;
    /*
    char t[25];
    sprintf(t,"%d",103);
    sprintf(t,"%d",10);
    string s=t;
    cout<<s<<endl;
     */
    return 0;
}
