## 학습 날짜

* 2020-12-23(수)

## 학습 내용

### xml

#### merge 태그

merge 태그는 레이아웃을 포함 할 때 중복 된 레이아웃을 제거하는 데 사용할 수 있으며 사용하는 것이 좋습니다.

중복 레이아웃의 예로는 시스템이 LinearLayout을 제거할 수 있는 Constratin Layout > Linear Layout > Text View 입니다.

이러한 종류의 최적화는 보기 계층 구조를 단순화하고 앱 성능을 향상시킬 수 있습니다. 

#### layout 태그

레이아웃 데이터는 데이터 바인딩을 활성화하기 위해 ```<layout>``` 요소로 래핑됩니다.
ConstraintLayout 및 기타 뷰는 ```<layout>``` 요소 내부에 정렬됩니다.
파일에는 placeholder인 ```<data>``` 태그가 있습니다.

### Step 1: Add SleepTrackerViewModel

이제 데이터베이스와 UI가 있으므로 데이터를 수집하고 데이터베이스에 데이터를 추가하고 데이터를 표시해야 합니다. 이 모든 작업은 뷰 모델에서 수행됩니다.
SleepTrackerViewModel은 버튼 클릭을 처리하고 DAO를 통해 데이터베이스와 상호 작용하며 LiveData를 통해 UI에 데이터를 제공합니다. 모든 데이터베이스 작업은 기본 UI스레드에서 실행되어야 하며 코루틴을 사용하여 수행합니다.

이 클래슨느 ViewModel과 동일하지만 애플리케이션 컨텍스트를 생성자 매개변수로 사용하고 속성으로 사용할 수 있도록 합니다.

### Step 2: Add SleepTrackerViewModelFactory

위의 ViewModel과 동일한 인수를 취하고 ViewModelProvider.Factory를 확장합니다.
팩토리 내에서 코드는 모든 클래스 유형을 인수로 사용하고 ViewModel을 반환하는 create()를 재정의합니다.
create() 본문에서 코드는 사용 가능한 SleepTrackerViewModel 클래스가 있는지 확인하고 있는 경우 해당 인스턴스를 반환합니다. 그렇지 않으면 코드에서 예외가 발생합니다.

### Step 3: Update SleepTrackerFragment

```kotlin
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.sleepTrackerViewModel = sleepTrackerViewModel
        return binding.root
    }
}
```

xml파일에서도 ```<data>``` 태그에 ```<variable>```을 추가합니다.

### 코루틴(Coroutine)

코루틴은 장기 실행 작업을 효율적으로 처리하는 방법입니다.
코틀린에서 코루틴을 사용하면 콜백 기반 코드를 순차 코드로 변환할 수 있습니다.
순차적으로 작성된 코드는 일반적으로 읽기가 더 쉽고 예외와 같은 언어 기능을 사용할 수도 있습니다. 결국 코루틴과 콜백은 동일한 작업을 수행합니다. 장기 실행 작업에서 결과를 사용할 수 있을때까지 기다렸다가 계속 실행합니다.

#### 코루틴의 속성

1. 코루틴은 비동기식이며 차단되지 않습니다.
2. 코루틴은 일시 중단 함수를 사용하여 비동기 코드를 순차적으로 만듭니다.

**코루틴은 비동기적입니다**

코루틴은 프로그램의 주요 실행 단계와 독립적으로 실행됩니다. 비동기의 중요한 측면 중 하나는 명시적으로 기다릴때까지 결과를 사용할 수 있다고 기대할 수 없다는 것입니다.

**코루틴은 비차단입니다**

비차단은 코루틴이 메인 또는 UI스레드를 차단하지 않음을 의미합니다. 따라서 코루틴을 사용하면 UI 상호 작용이 항상 우선 순위가 있기 때문에 사용자는 항상 가능한 가장 부드러운 경험을 갖게 됩니다.

**코루틴은 일시중단 함수를 사용하여 비동기 코드를 순차적으로 만듭니다**

suspend 키워드는 코루틴에서 사용할 수 있는 것으로 함수 또는 함수 유형을 표시하는 kotlin의 방법입니다.
코루틴이 정상 함수 호출처럼 함수가 반환될 때까지 차단하는 대신 suspend로 표시된 함수를 호출할 때 코루틴은 결과가 준비 될 때까지 실행을 중단합니다.
그런 다음 코루틴이 중단 된 지점에서 결과와 함께 다시 시작됩니다.

코루틴이 일시 중단되고 결과를 기다리는 동안 실행중인 스레드를 차단 해제합니다. 이렇게 하면 다른 함수나 코루틴이 실행될 수 있습니다.

suspend 키워드는 코드가 실행되는 스레드를 지정하지 않습니다. 일시 중단 기능은 백그라운드 스레드 또는 기본 스레드에서 실행될 수 있습니다.

#### 코루틴의 사용

코틀린에서 코루틴을 사용하려면 다음 세 가지가 필요합니다.

- A job
- A dispatcher
- A scope

**Job**

기본적으로 Job은 취소할 수 있는 모든 것입니다.
모든 코루틴에는 Job이 있으며 이 Job을 사용하여 코루틴을 취소할 수 있습니다.
Job은 상위/하위 계층으로 정렬 될 수 있습니다.
상위 Job을 취소하면 Job의 모든 하위 Job이 즉시 취소되므로 각 코루틴을 수동으로 취소하는 것보다 훨씬 편합니다.

**Dispatcher**

디스패처는 다양한 스레드에서 실행하기 위해 코루틴을 보냅니다.
예를 들어 Dispatchers.Main은 기본 스레드에서 작업을 실행하고 Dispatchers.IO는 차단 I/O 작업을 스레드의 공유 풀로 오프로드 합니다.

**Scope**

코루틴의 범위는 코루틴이 실행되는 컨텍스트를 정의합니다.
범위는 코루틴의 작업 및 디스패처에 대한 정보를 결합합니다.
범위는 코루틴을 추적합니다. 코루틴을 시작하면 범위에 포함 됩니다. 이는 코루틴을 추적 할 범위를 지정했음을 의미합니다.

#### 아키텍처 구성 요소가 있는 Kotlin 코루틴**

**CoroutineScope**

CoroutineScope는 모든 코루틴을 추적하여 코루틴이 실행되어야 하는 시기를 관리하는데 도움이 됩니다. 또한 시작된 모든 코루틴을 취소 할 수도 있습니다.
각 비동기 작업 또는 코루틴은 특정 범위 내에서 실행됩니다.

아키텍처 구성 요소는 앱의 논리적 범위에 대한 코루틴에 대한 최고 수준의 지원을 제공합니다.
아키텍처 구성 요소는 앱에서 사용할 수 있는 다음과 같은 기본 제공 범위를 정의합니다.
기본 제공 코루틴 범위는 각 해당 아키텍처 구성에 대한 KTX 확장에 있습니다.
이러한 범위를 사용할 때 적절한 종속성을 추가해야 합니다.

- ViewModelScope
- LifecycleScope
- liveData

**ViewModelScope**

ViewModelScope는 앱의 각 ViewModel에 대해 정의됩니다.
이 범위에서 시작된 모든 코루틴은 ViewModel이 지워지면 자동으로 취소됩니다.
이 튜토리얼에서는 ViewModelScope를 사용하여 데이터베이스 작업을 시작합니다.

#### Room and Dispatcher

Room 라이브러리를 사용하여 데이터베이스 작업을 수행 할 때 Room은 Dispatchers.IO를 사용하여 백그라운드에서 데이터베이스 작업을 수행합니다. Dispatcher를 명시적으로 지정할 필요는 없습니다.

### Step 4: Mark DAO functions as suspend functions

사용자는 다음과 같은 방법으로 수면 데이터와 상호 작용할 수 있기를 원합니다.

- 사용자가 시작 버튼을 탭하면 앱은 새로운 Sleep night를 생성하고 데이터베이스에 sleep night를 저장합니다.
- 사용자가 중지 버튼을 탭하면 앱이 종료 시간으로 night을 업데이트 합니다.
- 사용자가 지우기 버튼을 누르면 앱이 데이터베이스의 데이터를 지웁니다.

이러한 데이터베이스 작업은 시간이 오래 걸릴 수 있으므로 별도의 스레드에서 실행해야 합니다.

```kotlin
@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(night: SleepNight)

    @Update
    suspend fun update(night: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend fun get(key: Long): SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight(): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
}
```

### Step 5: Set up coroutines for database operations

다음 implementation을 추가합니다.

```xml
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

// Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:$room_version"
```

```kotlin
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()

    init {
        initializeTonight()
    }
    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }
}
```

tonight에는 현재의 night을 담아둡니다. MutableLiveData로 하는 이유는 데이터를 관찰하고 변경하기 위해서입니다.

### Step 6: Add the click handler for the Start button

시작 버튼을 눌렀을 때의 반응을 추가합니다.

```kotlin
    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        database.insert(night)
    }
```

위와 같이 시작 버튼을 눌렀을 때 동작할 함수를 작성합니다.
layout의 시작 버튼에 ```android:onClick="@{() -> sleepTrackerViewModel.onStartTracking()}"``` 문장을 추가합니다.

### Step 7: Display the data

```kotlin
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
    private val nights = database.getAllNights()
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }
```

다음과 같이 nights를 받아올 변수를 구성합니다. 이후 TextView 에서 nightsString을 출력하도록 합니다.

```
android:text="@{sleepTrackerViewModel.nightsString}"
```

Step 6에서의 방식으로 Stop과 Clear에 대해서도 동작을 완성합니다.

```kotlin
    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
        }
    }

    private suspend fun update(night: SleepNight) {
        database.update(night)
    }
```

```kotlin
    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear() {
        database.clear()
    }
```