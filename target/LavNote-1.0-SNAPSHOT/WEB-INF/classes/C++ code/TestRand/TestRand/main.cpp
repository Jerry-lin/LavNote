//
//  main.cpp
//  TestRand
//
//  Created by 游宇杰 on 2018/3/4.
//  Copyright © 2018年 游宇杰. All rights reserved.
//

#include <iostream>
using namespace std;
int h_in() {
    static int c = 0, r = rand();
    if (c >= 3) {
        c = 0;
        r = rand();
    }
    c++;
    return r;
}
int main(int argc, const char * argv[]) {
    // insert code here...
    cout<<(5523434>>5)%5<<endl;
    cout<<5523434%(32*5)<<endl;
    for (int i=0; i<10; i++) {
        cout<<"i的值"<<i<<",h_in函数产生的值:"<<h_in()<<endl;
    }
    return 0;
}
