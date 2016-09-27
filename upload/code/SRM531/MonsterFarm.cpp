#include <string>
#include <vector>
#include <map>
#include <cstdlib>
#include <cstring>
#include <cassert>
#include <set>
#include <iostream>
#include <sstream>
#include <cstddef>
#include <algorithm>
#include <utility>
#include <iterator>
#include <numeric>
#include <list>
#include <queue>
#include <complex>
using namespace std;

typedef long long LL;
typedef vector<int> vi;
typedef pair<int,int> pii;
#define rep(i,n) REP(i,0,n)
#define REP(i,a,b) for(int i=a;i<b;i++)
#define tr(c,i) for(decltype((c).begin()) i = (c).begin(); i != (c).end(); i++)
#define sz(c) int((c).size())
#define all(a) (a).begin(), (a).end()
#define mp(a,b) make_pair(a,b)
#define eps 1e-6
#define MOD 1000000007

int graph[55][55];
bool path[55][55];
int deg[55];
class MonsterFarm {
public:
	int numMonsters(vector<string> t) {
        int n=sz(t);
        memset(path,false,sizeof(path));
        memset(deg,0,sizeof(deg));
        rep(i,n) {
            istringstream is(t[i]);
            int j;
            while(is>>j) {
                path[i][j-1]=true;
                graph[i][j-1]++;
                deg[i]++;
            }
        }
        rep(k,n) rep(i,n) rep(j,n) {
            if(path[i][k]&&path[k][j]) path[i][j]=true;
        }
        rep(i,n) if(path[0][i]&&path[i][i]&&deg[i]>1) return -1;
        //simulate
        LL cur[n];
        LL next[n];
        memset(cur,0,sizeof(cur));
        cur[0]=1;
        rep(itr,60) {
            memset(next,0,sizeof(next));
            rep(i,n) {
                rep(j,n) {
                    next[j]+=cur[i]*graph[i][j];
                }
            }
            rep(i,n) cur[i]=next[i]%MOD;
        }
        int ret=0;
        rep(i,n) ret=(ret+cur[i])%MOD;
        return ret;
	}
};
