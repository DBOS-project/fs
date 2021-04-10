// async example
#include <iostream>
#include <future>

using namespace std;

// a non-optimized way of checking for prime numbers:
bool is_prime(long long x) {
    cout << "Calculating. Please, wait...\n";
    for (long long i=2; i<x; ++i)
        if (x % i == 0)
            return false;
    return true;
}

int main() {
    // long long num = 1225316126431;
    long long num1 = 12253161211;
    long long num2 = 1622531641;
    long long num3 = 1652834641;

    future<bool> fut1 = async(launch::async, is_prime, num1);
    future<bool> fut2 = async(launch::async, is_prime, num2);
    future<bool> fut3 = async(launch::async, is_prime, num2);
    future<bool> fut4 = async(launch::async, is_prime, num3);
    
    std::cout << "Checking for primes.\n";

    bool ret1 = fut1.get();      // waits
    bool ret2 = fut2.get();      // waits
    bool ret3 = fut3.get();      // waits
    bool ret4 = fut4.get();      // waits

    if (ret1) std::cout << "num 1 is prime!\n";
    else std::cout << "num 1 is not prime.\n";

    if (ret2) std::cout << "num 2 is prime!\n";
    else std::cout << "num 2 is not prime.\n";

    if (ret3) std::cout << "num 3 is prime!\n";
    else std::cout << "num 3 is not prime.\n";

    if (ret4) std::cout << "num 4 is prime!\n";
    else std::cout << "num 4 is not prime.\n";

    return 0;
}
