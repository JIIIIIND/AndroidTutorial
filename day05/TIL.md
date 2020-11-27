## 학습 날짜

* 2020-11-27(금)

## 학습 내용

### Fragment

#### 목적

다양한 크기의 화면을 가지는 단말기가 늘어남에 따라 한 화면에 여러 개의 화면 요소를 원하는 수요가 늘어가고 있습니다. 대표적으로 태블릿 PC의 경우 화면이 크기 때문에 복잡한 레이아웃 구성과 뷰 위젯 배치들로 인해 기존의 Activity를 통한 레이아웃 구성만으로는 구현하기 버거운 면이 있었습니다. 이에 안드로이드 3.0(API 11)부터 추가된것이 프래그먼트입니다.

#### 특징

프래그먼트는 액티비티 내에서 화면 UI의 일부를 나타냅니다. 여러 개의 프래그먼트를 조합하여 액티비티가 출력하는 한 화면의 UI를 표현할 수 있으며 하나의 프래그먼트를 다른 액티비티에 재사용할 수 있습니다. 액티비티처럼 하나의 독립된 모듈처럼 실행되기 때문에 액티비티와 연관된 생명주기를 가지고 있으며 액티비티 실행 중에도 동적으로 추가되거나 다른 프래그먼트로 교체가 가능합니다.

프래그먼트는 액티비티의 모듈식 섹션이라고 생각하면 됩니다. 이는 자체적인 수명 주기를 가지고, 자체 입력 이벤트를 수신하고, 액티비티 실행 중에 추가 및 삭제가 가능합니다.(다른 액티비티에 재사용할 수 있는 하위 액티비티와 같은 개념)

프래그먼트는 항상 액티비티 내에서 호스팅되어야 하며 해당 프래그먼트의 수명 주기는 호스트 액티비티의 수명 주기에 직접적으로 영향을 받습니다.
예를 들어 액티비티가 일시정지되는 경우, 그 안의 모든 프래그먼트도 일시정지되며 액티비티가 소멸하게 되면 모든 프래그먼트도 마찬가지로 소멸됩니다. 그러나 액티비티가 실행중인 동안에는 각 프래그먼트를 추가 또는 제거하는 등 개별적으로 조작할 수 있습니다.
이와 같은 프래그먼트 트랜잭션을 수행할 때는 이를 액티비티가 관리하는 백 스택에도 추가할 수 있습니다. 각 백 스택 항목이 발생한 프래그먼트 트랜잭션의 기록이 됩니다. 이 백 스택을 상요하면 사용자가 프래그먼트 트랜잭션을 거꾸로 돌릴 수 있습니다.(뒤로 이동) 이때 Back버튼을 누르면 됩니다.

프래그먼트를 액티비티 레이아웃에 추가하면, 해당 프래그먼트는 액티비티의 뷰 계층 내에서 ViewGroup에 들어가고 자체적인 뷰 레이아웃을 정의합니다. 액티비티의 레이아웃 파일에서 ```<fragment>``` 요소로 프래그먼트를 선언하거나 기존 ```ViewGroup```에 추가하는 방법으로 애플리케이션 코드에서 프래그먼트를 선언하면 액티비티 레이아웃에 프래그먼트를 삽입할 수 있습니다.

#### 디자인 철학

각 프래그먼트는 모듈식이고 재사용 가능한 액티비티 구성 요소로 디자인해야 합니다. 다시 말해, 각 프래그먼트가 자체적인 수명 주기 콜백으로 레이아웃과 동작을 정의하기 때문에 한 프래그먼트를 여러 액티비티에 포함할 수 있습니다. 그러므로 다시 사용할 것을 염두에 두고 디자인하고 하나의 프래그먼트를 다른 프래그먼트에서 직접 조작하는 것은 삼가야 합니다. 이것이 특히 중요한 이유는 모듈식 프래그먼트를 사용하면 프래그먼트 조합을 여러 가지 화면 크기에 맞춰 변경할 수 있기 때문입니다.

#### 프래그먼트 생성

프래그먼트를 생성하려면 Fragment의 하위 클래스(또는 이것의 기존 하위 클래스)를 생성해야 합니다.
Fragment 클래스에는 Activity와 아주 유사한 코드가 있습니다.
예를 들어 onCreate(), onStart(), onPause() 및 onStop() 등입니다.
기존 Android 애플리케이션ㅇ르 변환하여 프래그먼트를 사용하게 하려면 단순히 액티비티의 콜백 메서드에서 프래그먼트에 해당되는 콜백 메서드로 코들르 옮기기만 하면 될 수도 있습니다.

다음과 같은 수명 주기 메서드를 구현해야 합니다.

- onCreate()
	- 프래그먼트를 생성할 때 호출됩니다.
	- 기본 구성 요소 중 프래그먼트가 일시정지되거나 중단되었다가 재개되었을때 유지하고자 하는 것을 초기화해야 합니다.
- onCreateView()
	- 시스템은 프래그먼트가 자신의 사용자 인터페이스를 처음으로 그릴 시간이 되면 이것을 호출합니다.
	- 메서드에서 View를 반환해야 합니다.
	- 프래그먼트가 UI를 제공하지 않는 경우 null을 반환하면 됩니다.
- onPause()
	- 시스템이 이 메서드를 호출하는 것은 사용자가 프래그먼트를 떠난다는 것을 나타내는 첫 번째 신호입니다.(다만 항상 프래그먼트가 소멸 중이라는 것을 의미하지는 않습니다).
	- 현재 사용자 세션을 넘어서 지속되어야 하는 변경 사항을 커밋합니다(사용자가 돌아오지 않을 수 있기 때문).

대부분의 애플리케이션은 각각의 프래그먼트에 이와 같은 메서드를 최소한 세 개씩 구현해야 하지만, 프래그먼트 수명 주기의 여러 단계를 처리하는 데 사용해야 하는 다른 콜백 메서드도 많이 있습니다.

다만 종속적인 구성 요소의 코드 구현 수명 주기는 프래그먼트 콜백 구현에 직접 넣지 않고 구성 요소 자체에 두어야 합니다.

이외에도 기본적인 Fragment 클래스 대신 확장하고자 하는 하위 클래스도 몇 개 있을 수 있습니다.

- DialogFragment
	- 부동 대화상자를 표시
	- Activity 클래스의 대화상자 도우미 메서드를 사용하는 것의 좋은 대체 수단이 됩니다.
	- 프래그먼트 대화상잘르 액티비티가 관리하는 프래그먼트의 백 스택에 통합시킬 수 있어, 사용자가 닫힌 프래그먼트로 돌아갈 수 있기 때문
- ListFragment
	- 어댑터가 관리하는 항목의 목록을 표시하며 ListActivity와 비슷합니다.
	- 목록 뷰를 관리하는 데 쓰는 몇 가지 메서드를 제공합니다.
	- 레이아웃에 RecyclerView를 포함한 프래그먼트를 만들어야 합니다.
- PreferenceFragmentCompat
	- Preference 객체의 계층을 목록으로 표시합니다. 이는 애플리케이션에서 설정 화면을 만드는 데 사용합니다.

#### 사용자 인터페이스 추가

프래그먼트는 일반적으로 액티비티의 사용자 인터페이스의 일부로 사용되며 자체 레이아웃으로 액티비티에 기여합니다.

프래그먼트에 대해 레이아웃을 제공하려면 반드시 onCreateView() 콜백 메서드를 구현해야 합니다. 프래그먼트가 자신의 레이아웃을 그릴 때가 되면 Android 시스템이 이를 호출합니다. 이 메서드의 구현은 반드시 프래그먼트 레이아웃의 루트인 View를 반환해야 합니다.

onCreateView()로부터 레이아웃을 반환하려면 이를 XML에서 정의된 레이아웃 리소스로부터 팽창시키면 됩니다. 이를 돕기 위해 onCreateView()가 LayoutInflater 객체를 제공합니다.

예를 들어 다음은 Fragment의 하위 클래스입니다. 이것이 example_fragment.xml 파일로부터 레이아웃을 로드합니다.

```kotlin
class ExampleFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.example_fragment, container, false)
    }
}
```

onCreateView()로 전달된 container 매개변수가 상위 ViewGroup이며(액티비티 레이아웃으로부터), 이 안에 프래그먼트 레이아웃이 삽입됩니다.
```savedInstanceState``` 매개변수는 일종의 Bundle로, 이것은 프래그먼트가 재개되는 중인 경우 프래그먼트의 이전 인스턴스에 대한 데이터를 제공합니다.

inflate() 메서드는 다음과 같은 세 개의 인수를 취합니다.

- 팽창시키고자 하는 레이아웃의 리소스 ID.
- 팽창된 레이아웃의 상위가 될 ViewGroup
	- container를 전달해야 시스템이 레이아웃 매개변수를 팽창된 레이아웃의 루트 뷰에 적용할 수 있으므로 중요함
- 팽창된 레이아웃이 팽창 중에 ViewGroup에 첨부되어야 하는지를 나타낼 Bool값
	- 위의 예제에서는 false, 시스템이 이미 팽창된 레이아웃을 container 안에 삽입하기 때문
	- true를 전달하면 최종 레이아웃에 중복된 뷰 그룹을 생성하게 됩니다.

#### 액티비티에 프래그먼트 추가

일반적으로 프래그먼트는 UI의 일부분으로 호스트 액티비티에 참가하고, 호스트 액티비티는 해당 액티비티의 전체적인 뷰 계층의 일부분으로 포함되게 됩니다. 프래그먼트를 액티비티 레이아웃에 추가하는 데는 두 가지 방법이 있습니다.

**프래그먼트를 액티비티의 레이아웃 파일 안에서 선언**

이 경우, 프래그먼트에 대한 레이아웃 속성을 마치 뷰인 것처럼 나타낼 수 있습니다. 예를 들어 다음은 프래그먼트가 두 개 있는 액티비티에 대한 레이아웃 파일입니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <fragment android:name="com.example.news.ArticleListFragment"
            android:id="@+id/list"
            android:layout_weight="1"
            android:layout_width="0dp"
            android:layout_height="match_parent" />
    <fragment android:name="com.example.news.ArticleReaderFragment"
            android:id="@+id/viewer"
            android:layout_weight="2"
            android:layout_width="0dp"
            android:layout_height="match_parent" />
</LinearLayout>
```

```<fragment>``` 안의 ```android:name``` 특성은 레이아웃 안에서 인스턴스화할 Fragment 클래스를 지정합니다.

시스템은 이 액티비티 레이아웃을 생성할 때 레이아웃에서 지정된 각 프래그먼트를 인스턴스화하며 각각에 대해 onCreateView() 메서드를 호출하여 각 프래그먼트의 레이아웃을 검색합니다.
시스템은 프래그먼트가 반환한 View를 ```<fragment>``` 요소 자리에 곧바로 삽입합니다.

**프로그래밍 방식으로 프래그먼트를 기존의 ViewGroup에 추가**

액티비티가 실행 중인 동안에는 언제든 액티비티 레이아웃에 프래그먼트를 추가할 수 있습니다. 그저 프래그먼트를 배치할 ViewGroup을 지정하기만 하면 됩니다.

액티비티 내에서 프래그먼트 트랜잭션을 수행하려면(프래그먼트 추가, 제거, 교체) FragmentTransaction에서 가져온 API를 사용해야 합니다.
FragmentTransaction의 인스턴스를 FragmentActivity에서 가져오는 방법은 다음과 같습니다.

```kotlin
val fragmentManage = supportFragmentManager
val fragmentTransaction = fragmentManager.beginTransaction()
```

그런 다음, add() 메서드를 사용하여 프래그먼트를 추가하고, 추가할 프래그먼트와 이를 삽입할 뷰를 지정하면 됩니다.

```kotlin
val fragment = ExampleFragment()
fragmentTransaction.add(R.id.fragment_container, fragment)
fragmentTransaction.commit()
```

add()에 전달되는 첫 인수가 ViewGroup이고 여기에 프래그먼트는 리소스 ID가 지정한 대로 배치되어야 합니다. 두 번째 매개변수는 추가할 프래그먼트입니다.

FragmentTransaction을 변경하고 나면, 반드시 commit()을 호출해야 변경 내용이 적용됩니다.

#### 프래그먼트 관리

액티비티 내의 프래그먼트를 관리하려면 ```FragmentManager``` 를 사용해야 합니다.
이것을 가져오려면 액티비티에서 ```getSupporFragmentManager()```를 호출하세요.

```FragmentManager```를 가지고 할 수 있는 여러 가지 일은 다음과 같습니다.

- 액티비티 내에 존재하는 프래그먼트를 ```findFragmentById()```로 가져오거나(액티비티 레이아웃 내에서 UI를 제공하는 프래그먼트의 경우) 또는 ```fragmentByTag()```로 가져옵니다(UI를 제공하거나 하지 않는 프래그먼트의 경우)
- ```popBackStack()```을 사용하여 프래그먼트를 백 스택에서 꺼냅니다(사용자가 Back 명령을 시뮬레이션)
- 백 스택에 변경 내용이 있는지 알아보기 위해 ```addOnBackStackChangedListener()```로 리스너를 등록합니다.

```FragmentManager```를 사용해도 ```FragmentTransaction```을 열 수 있습니다. 이렇게 하면 프래그먼트 추가, 제거 등의 트랜잭션을 수행할 수 있습니다.

#### 프래그먼트 트랜잭션 수행

액티비티에서 프래그먼트를 사용하는 경우에는 특히 사용자 상호작용에 응답하여 추가, 제거, 교체 및 다른 작업을 수행할 수 있다는 점이 유용합니다. 액티비티에 커밋한 변경사항의 집합을 트랜잭션이라고 하며, 이것을 수행하려면 FragmentTransaction 내의 API를 사용하면 됩니다. 해당 액티비티가 관리하는 백 스택에 행해진 각 트랜잭션을 저장할 수도 있습니다. 이렇게 하면 사용자가 프래그먼트 변경사항에서 역으로 탐색할 수 있습니다.(액티비티를 역으로 탐색하는 것과 비슷합니다)

```FragmentTransaction```의 인스턴스를 ```FragmentManager```로부터 가져오는 방법은 다음과 같습니다.

```kotlin
val fragmentManager = supportFragmentManager
val fragmentTransaction = fragmentManager.beginTransaction()
```

각 트랜잭션은 동시에 수행하고자 하는 변경사항의 집합입니다. 주어진 트랜잭션에 대해 수행하고자 하는 모든 변경사항을 설정하려면 ```add()```, ```remove()``` 및 ```replace()```와 같은 메서드를 사용하면 됩니다. 그런 다음, 트랜잭션을 액티비티에 적용하려면 반드시 ```commit()```을 호출해야 합니다.

하지만 ```commit()```을 호출하기 전에 먼저 ```addToBackStack()```를 호출해야 합니다. 이렇게 해야 트랜잭션을 프래그먼트 트랜잭션의 백 스택에 추가할 수 있습니다. 이 백 스택은 액티비티가 관리하며, 사용자가 Back 버튼을 누르면 이를 통해 이전 프래그먼트 상태로 되돌아갈 수 있습니다.

예를 들어 다음은 한 프래그먼트를 다른 프래그먼트로 교체하고 이전 상태를 백 스택에 보존하는 방법을 보여줍니다.

```kotlin
val newFragment = ExampleFragment()
val transaction = supportFragmentManager.beginTransaction()
transaction.replace(R.id.fragment_container, newFragment)
transaction.addToBackStack(null)
transaction.commit()
```

이 예시에서는 ```newFragment```가 현재 레이아웃 컨테이너에서 식별된 프래그먼트를 ```R.id.fragment_container``` ID로 교체합니다.
```addToBackStack()```를 호출하면 교체 트랜잭션이 백 스택에 저장되므로, 사용자가 Back 버튼을 눌러 트랜잭션을 되돌리고 이전 프래그먼트를 다시 가져올 수 있습니다.

그러면 ```FragmentActivity```가 ```onBackPressed()```를 통해 백 스택에서 프래그먼트를 자동 검색합니다.

트랜잭션에 여러 개의 변경을 추가하고 ```addToBackStack()```을 호출하면, ```commit()```을 호출하기 전에 적용된 모든 변경사항이 백 스택에 하나의 트랜잭션으로 추가되며, Back 버튼을 누르면 모두 한꺼번에 되돌려집니다.

```FragmentTransaction```에 변경사항을 추가하는 순서는 중요하지 않습니다. 다만 다음과 같은 예외가 있습니다.

- ```commit()```을 마지막으로 호출해야 합니다.
- 같은 컨테이너에 여러 개의 프래그먼트를 추가하는 경우, 이를 추가하는 순서에 따라 이들이 뷰 계층에 나타나는 순서가 결정됩니다.

프래그먼트를 제거하는 트랜잭션을 수행하면서 ```addToBackStack()```을 호출하지 않는 경우, 해당 프래그먼트는 트랜잭션이 적용되면 소멸되고 사용자가 이를 되짚어 탐색할 수 없게 됩니다. 반면에 프래그먼트를 제거하면서 ```addToBackStack()```을 호출하면, 해당 프래그먼트는 중단되고 사용자가 뒤로 탐색할 때 다시 시작됩니다.

```commit()```을 호출하더라도 즉시 트랜잭션이 실행되지는 않습니다. 그보다는 스레드가 준비되는 즉시 최대한 빨리 액티비티의 UI 스레드("기본" 스레드)에서 이 트랜잭션이 수행되도록 일정을 예약하는 것에 가깝습니다. 하지만 필요한 경우 UI 스레드에서 ```executePendingTransactions()```를 호출하면 ```commit()``` 이 제출한 트랜잭션을 즉시 실행할 수 있습니다. 트랜잭션이 다른 스레드의 작업에 대한 종속성이 아니라면 굳이 이렇게 해야 할 필요는 없습니다.