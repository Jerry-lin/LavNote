//
//  main.cpp
//  HuaWei
//
//  Created by 游宇杰 on 2018/5/3.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include<iostream>
#include<stack>
#include<complex>
#include<vector>
#include<string>
using namespace std;
struct ListNode
{
    int val;
    ListNode *next;
    ListNode(int val) {
    this->val = val;
    this->next = NULL;
    }
};
struct TreeNode
{
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int val)
    {
        this->val=val;
        this->left=this->right=NULL;
    }
};
int binarySearch(vector<int> &nums, int target) {
    // write your code here
    if(nums.size()==0) return -1;
    int low=0;
    int high=nums.size()-1;
    while(low<=high)
    {
        int mid=(low+high)/2;
        if(nums[mid]==target)
        {
            if(mid==0) return mid;
            if(nums[mid]>nums[mid-1]) return mid;
            else
            {
                high=mid-1;
            }
            continue;
        }
        if(nums[mid]>target)
        {
            high=mid-1;
        }
        else if(nums[mid]<target)
        {
            low=mid+1;
        }
    }
    return -1;
}

ListNode * addLists(ListNode * l1, ListNode * l2) {
    // write your code here
    if(l1==NULL) return l2;
    else if(l2==NULL) return l1;
    int num=0;
    ListNode *p1=l1;
    ListNode *p2=l2;
    ListNode *head=l1;
    ListNode *tail=NULL;
    ListNode *tail2=NULL;
    while(p1&&p2)
    {
        int a=p1->val;
        p1->val=(a+p2->val+num)%10;
        num=(a+p2->val+num)/10;
        tail=p1;
        tail2=p2;
        p1=p1->next;
        p2=p2->next;
    }
    if(p1)
    {
        while(p1)
        {
            int a=p1->val;
            p1->val=(a+num)%10;
            num=(a+num)/10;
            tail=p1;
            p1=p1->next;
        }
    }
    if(p2)
    {
        tail->next=p2;
        while(p2)
        {
            int a=p2->val;
            p2->val=(a+num)%10;
            num=(a+num)/10;
            tail=p2;
            p2=p2->next;
        }
        
        
    }
    if(num)
    {
        tail=new ListNode(num);
        num=0;
    }
    return head;
}
ListNode * partition(ListNode * head, int x) {
    // write your code here
    if(head==NULL) return NULL;
    ListNode* p1=NULL;
    ListNode* p2=NULL;
    ListNode* p=head;
    ListNode* head1=NULL;
    ListNode* head2=NULL;
    int i=0;
    while(p)
    {
        if(i==0)
        {
            if(p->val<x)
            {
                head1=p;
                p1=p;
                p1->next=NULL;
            }
            else
            {
                head2=p;
                p2=p;
                p2->next=NULL;;
            }
        }
        else
        {
            if(p->val<x)
            {
                if(p1==NULL)
                {
                    head1=p;
                    p1=p;
                    p1->next=NULL;
                }
                else
                {
                    p1->next=p;
                    p1=p;
                    p1->next=NULL;
                }
                
            }
            else
            {
                if(p2==NULL)
                {
                    head2=p;
                    p2=p;
                    p2->next=NULL;
                }
                else
                {
                    p2->next=p;
                    p2=p;
                    p2->next=NULL;
                }
                
            }
        }
        i++;
        p=p->next;
    }
    if(p1==NULL) return head2;
    p1->next=head2;
    return head1;
}
bool isUnique(string &str) {
    // write your code here
    if(str.size()==0) return true;
    sort(str.begin(),str.end());
    int low=0;
    int high=str.size();
    while(low<high)
    {
        if(str[low]==str[low+1]) return false;
        else
            low++;
    }
    return true;
}

vector<vector<int>> threeSum(vector<int> &numbers) {
    vector<vector<int>> result;
    int length=numbers.size();
    if(length<3) return result;
    sort(numbers.begin(),numbers.end());
    int low=0;
    int high=length-1;
    int p=1;
    vector<int> tmp;
    while(low<high)
    {
        if(p<high&&numbers[low]+numbers[high]+numbers[p]==0)
        {
            
            vector<int> tmp{numbers[low],numbers[high],numbers[p]};
            result.push_back(tmp);
            while(p<length-1)
            {
                if(numbers[p]==numbers[p+1]) p++;
                else break;
            }
            while(low<length-1)
            {
                if(numbers[low]==numbers[low+1]) low++;
                else break;
            }
            while(high>0)
            {
                if(numbers[high]==numbers[high-1]) high--;
                else break;
            }
            if(p>=high-1)
            {
                low++;
                p=low+1;
            }
            else if(p>low) p++;
            else
                p=low+1;
        }
        else if(p<high&&numbers[low]+numbers[high]+numbers[p]>0)
        {
            p=low+1;
            high--;
        }
        else
        {
            if(p>=high-1)
            {
                low++;
                p=low+1;
            }
            else p++;
        }
    }
    return result;
}

ListNode * reverseBetween(ListNode * head, int m, int n) {
    // write your code here
    if(head==NULL) return NULL;
    ListNode* head1=NULL;
    ListNode* l1=NULL;
    int i=1;
    ListNode* p=head;
    while(i<m)
    {
        if(i==1)
        {
            head1=p;
            l1=p;
        }
        else
        {
            l1=p;
            p=p->next;
        }
        i++;
    }
    ListNode* l2=p->next;
    ListNode* prev=NULL;
    ListNode* next=NULL;
    int count=0;
    while(i<=n)
    {
       
        next=l2->next;
        l2->next=prev;
        prev=l2;
        l2=next;
        if(count==0)
        {
            p=prev;
            count++;
        }
        i++;
    }
    if(l1==NULL)
    {
        p->next=next;
        return prev;
    }
    l1->next=prev;
    p->next=next;
    return head1;
}

TreeNode* buildTreeHelper(vector<int> &preorder,int start1,int end1,vector<int> &inorder,int start2,int end2)
{
    //前序第一个就是根节点
    int rootValue=preorder[start1];
    //创建一个节点作为节点
    TreeNode* root=new TreeNode(rootValue);
    //如果中序和后序都是一个节点，那么直接返回
    if(start1==end1)
    {
        if(start2==end2)
            return root;
    }
    //以start2为起点，在中序遍历中找到该值
    int pos=start2;
    //中序遍历中找到这个值
    while(inorder[pos]!=rootValue)
    {
        pos++;
    }
    //左子树起点
    int leftStart=start2+1;
    //找出左子树长度
    int leftLength=pos-start2;
    //右子树起点
    int rightStart=pos+1;
    if(leftLength>0)
        root->left=buildTreeHelper(preorder, start1+1, start1+leftLength, inorder,start2, pos-1);
    if(end2-start2>leftLength)
    root->right=buildTreeHelper(preorder, start1+leftLength+1, end1,inorder,pos+1, end2);
    return root;
}

TreeNode * buildTree(vector<int> &preorder, vector<int> &inorder) {
    // write your code here
    if(preorder.size()==0||inorder.size()==0||inorder.size()!=preorder.size()) return NULL;
    return buildTreeHelper(preorder,0,preorder.size()-1,inorder,0,inorder.size()-1);
}

int main(int argc, const char * argv[]) {
    /*
    ListNode* head=new ListNode(1);
    head->next=new ListNode(2);
    head->next->next=new ListNode(3);
    head->next->next->next=new ListNode(4);
    head->next->next->next->next=new ListNode(5);
    head->next->next->next->next->next=NULL;
    reverseBetween(head,2,4);
    */
    vector<int> preorde{6,2,3,4,5};
    vector<int> inorder{2,4,5,3,6};
    TreeNode* root=buildTree(preorde, inorder);
    return 0;
}
