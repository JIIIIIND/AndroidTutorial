## 학습 날짜

* 2020-12-14(월)

## 학습 내용

### ViewModel

수명 주기를 고려하여 UI 관련 데이터를 저장하고 관리하도록 설계되었습니다. ViewModel 클래스를 사용하면 화면 회전과 같이 구성을 변경할 때도 데이터를 유지할 수 있습니다.

Android 프레임워크는 Activity 및 Fragment와 같은 UI 컨트롤러의 수명 주기를 관리합니다. 프레임워크는 특정 사용자 작업이나 완전히 통제할 수 없는 기기 이벤트에 대한 응답으로 UI 컨트롤러를 제거하거나 다시 만들도록 결정할 수 있습니다.

시스템에서 UI 컨트롤러를 제거하거나 다시 만들면 컨트롤러에 저장된 일시적인 모든 UI 관련 데이터가 손실됩니다.
예를 들어 Activity 중 하나에 사용자 목록을 포함할 수 있습니다. 구성 변경을 위해 활동을 다시 생성하면 새 활동은 사용자 목록을 다시 가져와야 합니다.
데이터가 단순한 경우 활동은 ```onSaveInstanceState()``` 메서드를 사용하여 ```onCreate()```의 번들에서 데이터를 복원할 수 있습니다.
하지만 이 접근 방법은 사용자 목록이나 비트맵과 같은 대용량일 가능성이 높은 데이터가 아니라, 직렬화했다가 다시 역직렬화할 수 있는 소량의 데이터에만 적합합니다.

또 다른 문제는 UI 컨트롤러가 반환하는 데 시간이 걸릴 수 있는 비동기 호출을 자주 해야 한다는 점입니다. UI 컨트롤러는 이러한 비동기 호출을 관리해야 하며, 메모리 누출 가능성을 방지하기 위해 시스템에서 호출 폐기 후 호출을 정리하는지 확인해야 합니다.
이러한 관리에는 많은 유지보수가 필요하며, 구성 변경 시 개체가 다시 생성되는 경우 개체가 이미 실행된 호출을 다시 해야 할 수 있으므로 리소스가 낭비됩니다.

#### ViewModel의 수명 주기

ViewModel 객체의 범위는 ViewModel을 가져올 때 ViewModelProvider에 전달되는 Lifecycle로 지정됩니다. ViewModel은 범위가 지정된 Lifecycle이 영구적으로 경과될 때까지, 즉 활동에서는 활동이 끝날 때까지 그리고 프래그먼트에서는 프래그먼트가 분리될 때까지 메모리에 남아 있습니다.

### LiveData

LiveData는 식별 가능한 데이터 홀더 클래스입니다. 식별 가능한 일반 클래스와 달리 LiveData는 수명 주기를 인식합니다. 즉 활동, 프래그먼트 또는 서비스와 같은 다른 앱 구성요소의 수명 주기를 고려합니다. 이러한 수명 주기 인식을 통해 LiveData는 활성 수명 주기 상태에 있는 앱 구성요소 관찰자만 업데이트합니다.

Observer 클래스로 표현되는 관찰자의 수명 주기가 STARTED 또는 RESUMED 상태이면 LiveData는 관찰자를 활성 상태로 간주합니다. LiveData는 활성 관찰자에게만 업데이트 정보를 알립니다. LiveData 객체를 보기 위해 등록된 비활성 관찰자는 변경사항에 관한 알림을 받지 않습니다.

LifecycleOwner 인터페이스를 구현하는 객체와 페어링된 관찰자를 등록할 수 있습니다. 이 관계를 사용하면 관찰자에 대응되는 Lifecycle 객체의 상태가 DESTROY로 변경될 때 관찰자를 삭제할 수 있습니다. 특히 활동과 프래그먼트가 LiveData 객체를 안전하게 관찰할 수 있고 활동과 프래그먼트의 수명 주기가 끝나는 즉시 수신 거부되어 누출을 걱정하지 않아도 되므로 유용합니다.

#### LiveData 사용의 이점

**UI와 데이터 상태의 일치 보장**

LiveData는 관찰자 패턴을 따릅니다. LiveData는 수명 주기 상태가 변경될 때 Observer 객체에 알립니다. 코드를 통합하여 이러한 Observer 객체에 UI를 업데이트 할 수 있습니다. 앱 데이터가 변경될 때마다 UI를 업데이트 하는 대신, 변경이 발생할 때마다 관찰자가 UI를 업데이트할 수 있습니다.

**메모리 누출 없음**

관찰자는 Lifecycle 객체에 결합되어 있으며 연결된 수명 주기가 끝나면 자동으로 삭제됩니다.

**중지된 활동으로 인한 비정상 종료 없음**

관찰자의 수명 주기가 비활성 상태이면 관찰자는 어떤 LiveData 이벤트도 수신하지 않습니다.

**수명 주기를 더 이상 수동으로 처리하지 않음**

UI 구성요소는 관련 데이터를 관찰하기만 할 뿐 관찰을 중지하거나 다시 시작하지 않습니다. LiveData는 관찰하는 동안 관련 수명 주기 상태의 변경을 인식하므로 이 모든 것을 자동으로 관리합니다.

**최신 데이터 유지**

수명 주기가 비활성화되면 다시 활성화될 때 최신 데이터를 수신합니다. 예를 들어 백그라운드에 있었던 활동은 포그라운드로 돌아온 직후 최신 데이터를 받습니다.

**적절한 구성 변경**

구성 변경(기기 회전)으로 인해 활동이나 프래그먼트가 다시 생성되면 사용할 수 있는 최신 정보를 즉시 수신합니다.

**리소스 공유**

앱에서 시스템 서비스를 공유할 수 있도록 싱글톤 패턴을 사용하는 LiveData 객체를 확장하여 시스템 서비스를 래핑할 수 있습니다.
LiveData 객체가 시스템 서비스에 한번 연결되면 리소스가 필요한 모든 관찰자가 LiveData 객체를 볼 수 있습니다.

### 레트로핏

#### Step 1. Add Retrofit dependencies to Gradle

1. Open build.gradle(Module:app)
2. dependencies 추가

```xml
implementation "com.squareup.retrofit2:retrofit:$version_retrofit"
implementation "com.squareup.retrofit2:converter-scalars:$version_retrofit"
```

#### Step 2. Add support for Java 8 language features

build.gradle 파일에서 다음을 추가함

```xml
android {
	...

	compileOptions {
		sourceCompatibility JavaVersion.VERSION_1_8
		targetCompatibility JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
}
```

#### Step 3. Implement ApiService

여기서는 MarsApiService를 사용합니다.

1. ```app/java/network/MarsApiService.kt```에 다음 내용을 추가함

```kotlin
private const val BASE_URL =
	"https://android-kotlin-fun-mars-server.appspot.com"
```

2. Retrofit 빌더를 사용하여 Retrofit 오브젝트를 생성합니다.

```kotlin
private val retrofit = Retrofit.Builder()
	.addConverterFactory(ScalarsConverterFactory.create())
	.baseUrl(BASE_URL)
	.build()
```

웹 서비스 API를 구축하기 위해 기본 URL과 Converter Factory를 필요로 합니다.
Converter는 Retrofit에게 웹 서비스에서 가져온 데이터로 무엇을 하는지 알려줍니다.
이 경우에는 Retrofit이 웹 서비스에서 JSON 응답을 가져와서 문자열로 반환하기를 원합니다.
Retrofit에는 문자열 및 기타 기본 유형을 지원하는 ScalarConverter가 있으므로 ScalarsConverterFactory의 인스턴스를 사용하여 빌더에서 addConverterFactory()를 호출합니다. 마지막으로 build()를 호출하여 Retrofit 객체를 만듭니다.

3. 인터페이스를 정의합니다.

```kotlin
interface MarsApiService {
	@GET("relestate")
	fun getProperties():
		Call<String>
}
```

현재 목표는 웹 서비스에서 JSON 응답 문자열을 가져오는 것이며 이를 위해서는 getProperties() 메서드 하나만 필요합니다. Retrofit에 이 메서드가 수행해야 하는 작업을 알리려면 @GET 주석을 사용하고 해당 웹 서비스 메서드에 대한 경로 또는 끝점을 지정합니다.
이 경우 끝점을 realestate라고 합니다. getProperties() 메서드가 호출되면 Retrofit은 끝점 realestate를 기본 URL(Retrofit 빌더에서 정의한)에 추가하고 Call 객체를 만듭니다. 해당 Call 객체는 요청을 시작하는 데 사용됩니다.

4. public object를 작성합니다.

```kotlin
object MarsApi {
	val retrofitService : MarsApiService by lazy {
		retrofit.create(MarsApiService::class.java) }
}
```

Retrofit create() 메서드는 MarsApiService 인터페이스를 사용하여 Retrofit 서비스 자체를 만듭니다. 이 호출은 비용이 많이 들고 앱에 Retrofit 서비스 인스턴스가 하나만 필요하기 때문에 MarsApi라는 공용 개체를 사용하여 나머지 앱에 서비스를 노출하고 거기에서 Retrofit 서비스를 느리게 초기화합니다. 이제 모든 설정이 완료되었으므로 앱이 MarsApi.retrofitService를 호출할때마다 MarsApiService를 구현하는 단일 Retrofit 개체를 가져옵니다.

#### Step 4. Call the web service in OverviewViewModel

1. app/java/overview/OverviewViewModel.kt 내부의 getMarsRealEstateProperties()메서드 수정

```kotlin
private fun getMarsRealEstateProperties() {
	MarsApi.retrofitService.getProperties().enqueue(
		object: Callback<String> {
			override fun onFailure(call: Call<String>, t: Throwable) {
				_response.value = "Failure: " + t.message
			}

			override fun onResponse(call: Call<String>,
				response: Response<String>) {
					_response.value = response.body()
				}
		})
}
```

#### Step 5. Define the internet permission

AndroidManifest.xml 파일에 인터넷 권한을 넣어주어야 제대로 동작합니다.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```
