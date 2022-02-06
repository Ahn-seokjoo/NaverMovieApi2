## NaverApi 2 - Android
---
### **설명**
* 안드로이드 기본기를 연습하다가 갑자기 만들어본 프로젝트입니다.
  MVVM을 이용해 만들었으며, RxJava의 debounce를 통해 검색어를 입력하면, Naver Movie Api를 이용해 영화를 검색하고, 리사이클러뷰에 표시합니다.
  후에 Room db에 저장하고 삭제기능 또한 추가했습니다.
  영화 포스터 클릭 시 상세 페이지로 이동하며, 상세 페이지에서 한번 더 클릭시 Web에서 영화 검색 결과를 보여줍니다.
  
---
### **실행 영상**  
#
  - ![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/67602108/118836521-e0580180-b8fe-11eb-9727-7a792a1955de.gif)
  - 

### **사용 기술 스택**
--- 
1. [Glide](https://github.com/bumptech/glide)
2. [Retrofit2](https://github.com/square/retrofit)
3. [RxJava3](https://github.com/ReactiveX/RxJava) & [RxKotlin](https://github.com/ReactiveX/RxKotlin) & [RxAndroid](https://github.com/ReactiveX/RxAndroid)
4. [RxBinding](https://github.com/JakeWharton/RxBinding)
5. [Room](https://developer.android.com/training/data-storage/room?hl=ko)
6. [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
7. [hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=ko)
8. [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=ko)
9. [Databinding](https://developer.android.google.cn/topic/libraries/data-binding?hl=en)
### 프로젝트 구조
```kotlin
com.example.githubsearchapp
├── MovieApplication
├── data
│    ├── local
│    │     └── room
│    │     │     └── MovieDao
│    │     │     └── MovieDatabase
│    │     ├── MovieLocalRepository
│    │     ├── MovieLocalRepositoryImpl
│    ├── remote
│    │     ├── MovieApi
│    │     ├── MovieRemoteRepository
│    │     └── MovieRemoteRepositoryImpl
│    ├── MovieRepository
│    ├── MovieRepositoryImpl
├── entity
     └── MovieResult
├── ext
│    ├── BindingAdapter
│    └── MovieModule
├── view
│    ├── DetailFragment
│    ├── MainActivity
│    ├── MovieAdapter
│    └── MovieFragment
└── viewmodel
     └── MovieViewModel
```
### data
- local.room
```MovieDao```, ```MovieDatabase```를 이용해 Room db를 사용합니다.(추가, 삭제, 리스트 요청 기능)

 ```MovieLocalRepository(interface)```, ```MovieLocalRepositoryImpl(class)``` 에서 movieDao를 이용해 local db작업을 진행합니다. 
 
 리스트 요청 작업은 ```RxJava```의```Single```, 추가, 삭제는 ```Completable```을 이용합니다.
 
 - remote
 ```RxJava```의 ```Single```을 사용, Naver Api를 이용한 영화 리스트를 받아옵니다.
 remote에서 사용할 ```MovieApi```또한 가지고 있습니다. (본 예제는 리스트를 가져오는 함수 한개만 있습니다.) 
 
 - repository 
   remote, local repository를 동시에 가지고 있습니다. 두개를 이용해 repository에서 수행할 동작들을 모아두고, 
   두개를 한곳에 모아둔 repository를 주입 받아서 remote와 local을 한번에 사용할 수 있습니다.
 
 ### entity
 - MovieResult
  remote로 넘어올 데이터 형식을 선언해둡니다.
  
 ### ext
 - BindingAdapter
  xml에서 사용할 BindingAdapter 입니다.
 - MovieModule 
  Hilt에서 사용할 MovieModule을 만듭니다. MovieModule에서는 Retrofit, OkhttpClient, MovieApi, MovieDao, Database, Repository를 주입해줍니다.
  
### view
- DetailFragment
 영화를 클릭했을 때 상세정보를 보여줍니다. RatingBar를 이용해 별점을 보여주며, 영화 포스터, 제목, 감독, 배우를 보여줍니다. 또한 포스터 클릭시 해당 영화를 네이버에 검색한 web page를 실행합니다.
- MainActivity
영화 검색 EditText를 가지고 있습니다. RxBinding을 이용해 debounce(1000L)로 영화 정보를 검색합니다. 
또한 뒤로가기 2번 클릭시 종료 기능 또한 추가했습니다.
- MovieAdapter
영화 목록을 ListAdapter를 이용해 데이터를 bind 해줍니다.
LinearLayout을 이용했으며, 클릭, 롱클릭 기능을 구현했습니다.
클릭시에는 DetailFragment로, 롱클릭시에는 삭제 여부를 물어봅니다.
- MovieFragment
영화 목록을 리스트 형식으로 보여줍니다. ViewModel에서 movieList를 LiveData형식으로 가지고 있으며, 이를 observe하여 submitList로 목록을 업데이트 해줍니다.

### viewmodel
- MovieViewModel
AAC ViewModel로, Movie List와 Progressbar의 상태를 가지고 있습니다.
이전에 repository를 주입받아 network, db 기능을 수행합니다. 

### **개발 환경**
---
- 언어 - **Kotlin**
- minSdkVersion - 26
- targetSdkVersion - 31
- target - Android 12.0(Google APIs) 
- testDevice - Pixel2 api 31

### 아쉬운점
Repository에서 local과 remote를 완벽하게 합치지 못한 것 같습니다. 안드로이드 기본기를 연습하다 갑작스레 만든 프로젝트이다보니,
local과 remote에서의 리턴 값이 ```Single<List<MovieResult.Item>>```, ```Single<MovieResult>```로 달라 Repository에서 함수 한개가 추가됐습니다.
완벽한 캡슐화를 하고싶었다면, ```getMovieList```와 ```getRemoteMovieList```를 하나로 합쳐 ```getMovieList``` 하나의 함수로 만들고,
remote와 local 구분 없이 ViewModel 단에서 데이터만 요청하는 구성으로 만들었으면 더 좋았을 것 같습니다.
