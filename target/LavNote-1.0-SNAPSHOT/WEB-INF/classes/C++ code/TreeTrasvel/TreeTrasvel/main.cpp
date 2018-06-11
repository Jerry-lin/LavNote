//
//  main.cpp
//  TreeTrasvel
//
//  Created by 游宇杰 on 2018/4/27.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
#include <string>
using namespace std;
struct treeNode{
    string value;
    treeNode *leftChild=NULL;
    treeNode *rightChild=NULL;
};
void prefixTrasvel(treeNode *tree)
{
    if(tree==NULL) return ;
    cout<<tree->value<<" ";
    prefixTrasvel(tree->leftChild);
    prefixTrasvel(tree->rightChild);
}

void midTrasvel(treeNode *tree)
{
    if(tree==NULL) return ;
    midTrasvel(tree->leftChild);
    cout<<tree->value<<" ";
    midTrasvel(tree->rightChild);
}

void postTrasvel(treeNode *tree)
{
    if(tree==NULL) return ;
    postTrasvel(tree->leftChild);
    postTrasvel(tree->rightChild);
    cout<<tree->value<<" ";
}
int main(int argc, const char * argv[]) {
    treeNode *root=new treeNode;
    root->value="A";
    
    treeNode *left1=new treeNode;
    left1->value="B";
    root->leftChild=left1;

    treeNode *right1=new treeNode;
    right1->value="C";
    root->rightChild=right1;
    
    treeNode *left1right1=new treeNode;
    left1right1->value="D";
    left1->rightChild=left1right1;
    
    treeNode *right1left1=new treeNode;
    right1left1->value="E";
    right1->leftChild=right1left1;
    
    treeNode *right1right1=new treeNode;
    right1right1->value="F";
    right1->rightChild=right1right1;
    
    treeNode *right1left1left=new treeNode;
    right1left1left->value="G";
    right1left1->leftChild=right1left1left;
    
    treeNode *right1right1left=new treeNode;
    right1right1left->value="H";
    right1right1->leftChild=right1right1left;
    
    treeNode *right1right1right=new treeNode;
    right1right1right->value="I";
    right1right1->rightChild=right1right1right;
    
    prefixTrasvel(root);
    cout<<endl;
    midTrasvel(root);
    cout<<endl;
    postTrasvel(root);
    return 0;
}
