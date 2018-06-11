//
//  main.cpp
//  JD
//
//  Created by 游宇杰 on 2018/4/9.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
#include <cmath>
#include<climits>
using namespace std;

int main(int argc, const char * argv[]) {
    int length=0;
    cin>>length;
    int *p=new int[length];
    for (int i=0; i<length; i++) {
        cin>>p[i];
    }
    int x=sqrt(INT_MAX);
    int y=sqrt(INT_MAX);
    int count=0;
    for (int i=0; i<length; i++) {
        if(p[i]%2!=0)
        {
            cout<<"No"<<endl;
            continue;
        }
        int t=sqrt(p[i]);
        for (int j=t; j>1; --j) {
            if(p[i]%j==0&&(j+p[i]/j)%2!=0)
            {
                if((p[i]/j)%2==0)
                {
                    if(y>p[i]/j)
                    {
                        y=p[i]/j;
                        x=j;
                    }
                }
                else{
                    if(y>j)
                    {
                        x=p[i]/j;
                        y=j;
                    }
                }
                count++;
            }
        }
        if(count==0) cout<<"No"<<endl;
        else
        {
            cout<<x<<" "<<y<<endl;
            x=sqrt(INT_MAX);
            y=sqrt(INT_MAX);
        }
        count=0;
    }
    return 0;
}

