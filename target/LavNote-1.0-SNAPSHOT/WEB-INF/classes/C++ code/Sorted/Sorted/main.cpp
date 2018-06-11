#include<iostream>
#include<algorithm>
#include<vector>
#include<utility>
using namespace std;
bool compareToPair(pair<int,int> &a, pair<int,int> &b)
{
    return a.first<b.first;
}
int main()
{
    //第一个存储新的id，第二个存储旧id
    pair<int,int> ids;
    vector<pair<int,int>> sets;
    sets.push_back(make_pair(1,0));
    sets.push_back(make_pair(7,19));
    sets.push_back(make_pair(2,6));
    sets.push_back(make_pair(5,7));
    sets.push_back(make_pair(4,8));
    sets.push_back(make_pair(6,18));
    sets.push_back(make_pair(3,5));
    cout<<"排序之前:"<<endl;
    for (int i=0; i<sets.size(); i++) {
        cout<<sets[i].first<<" "<<sets[i].second<<endl;
    }
    sort(sets.begin(),sets.end(),compareToPair);
    cout<<"排序之后:"<<endl;
    for (int i=0; i<sets.size(); i++) {
        cout<<sets[i].first<<" "<<sets[i].second<<endl;
    }
    return 0;
}
