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

int dp[35][35][10][1<<9];   //N,M,K,mask
class DengklekBuildingRoads {
public:
	int numWays(int N,int M,int K) {
        dp[N][M][0][0]=1;
        for(int n=N-1;n>=0;n--) {
            for(int m=M;m>=0;m--) {
                for(int mask=0;mask<1<<(K+1);mask++) {
                    if(((1<<K)&mask)==0)
                        dp[n][m][K][mask]=dp[n+1][m][0][mask<<1];
                }
                for(int k=K-1;k>=0;k--) {
                    for(int mask=0;mask<1<<(K+1);mask++) {
                        dp[n][m][k][mask]=dp[n][m][k+1][mask];
                        if(m+1<=M&&n-k-1>=0) {
                            dp[n][m][k][mask]+=dp[n][m+1][k][mask^(1<<0)^(1<<(k+1))];
                            dp[n][m][k][mask]%=MOD;
                        }
                    }
                }
            }
        }
        return dp[0][0][0][0];
	}
};

//int main() {
//    DengklerkBuildingRoads test;
//    cout<<test.numWays(10, 20, 5)<<endl;
//}