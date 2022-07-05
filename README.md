# ExploreOkHttp

An exploratory console application written in [kotlin](https://kotlinlang.org/) to showcase how to
use [OkHttp](https://square.github.io/okhttp/) a modern applications HTTP client.

## What can you find?
- Use and configure OkHttpClient.Builder to get OkHttpClient.
- Add interceptors, such as
  - HttpLoggingInterceptor
  - custom interceptors, serving different purposes, such as authentication, or add custom headers
  - Network layer interceptor ***(such as to enable caching)***
  - Application layer network interceptor ***(enforce cache to be used)***

### OkHttp Interceptor design
<img src="misc/images/interceptor_architecture.png" width="336" align="top" hspace="20">

### Demo for network caching using interceptors
<img src="misc/images/network_cache.gif" width="600" align="top" hspace="20">

### Web Sevice
- [jsonplaceholder posts API](https://jsonplaceholder.typicode.com/posts) is used in order to showcase API call capabilities ok OkHttp

### Dependencies
- com.squareup.okhttp3:okhttp:4.10.0
- com.squareup.okhttp3:logging-interceptor:4.10.0
- com.google.code.gson:gson:2.9.0

**[TODO]** Explore authenticator use cases.

## References
- [OkHttp](https://square.github.io/okhttp/) - official site
- [mindorks okhttp interceptors](https://blog.mindorks.com/okhttp-interceptor-making-the-most-of-it) - nice use cases of interceptors
- [objectpartners blog on authenticator](https://objectpartners.com/2018/06/08/okhttp-authenticator-selectively-reauthorizing-requests/) - explain in detail usage of authenticator
- [baeldung okhttp interceptors](https://www.baeldung.com/java-okhttp-interceptors)
- [SO - How to cache OkHttp response from Web server?](https://stackoverflow.com/a/49455438/2694480)
- [A complete guide to okhttp](https://blog.logrocket.com/a-complete-guide-to-okhttp/)