## 학습 날짜

* 2020-11-30(월)

## 학습 내용

### 액티비티와 통신

Fragment는 FragmentActivity로부터 독립적인 객체로 구현되었고 여러 개의 액티비티 안에서 사용할 수 있는 것이 사실이지만, 프래그먼트의 주어진 인스턴스는 그것을 포함하고 있는 액티비티에 직접적으로 연결되어 있습니다.

이 프래그먼트는 getActivity()를 사용하여 FragmentActivity 인스턴스에 액세스하고 액티비티 레이아웃에서 뷰를 찾는 등의 작업을 손쉽게 수행할 수 있습니다.

```kotlin
val listView: View? = activity?.findViewById(R.id.list)
```

이와 마찬가지로 액티비티도 프래그먼트 안의 메서드를 호출할 수 있습니다. 그러려면 FragmentManager에서 Fragment에 대한 참졸르 가져와야 하는데, 이때 findFragmentById() 또는 findFragmentByTag()를 사용합니다. 예를 들면 다음과 같습니다.

```kotlin
val fragment = supportFragmentManager.findFragmentById(R.id.example_fragment) as ExampleFragment
```

#### 액티비티에 대한 이벤트 콜백 생성

액티비티 및/또는 액티비티가 호스팅하는 다른 프래그먼트로 이벤트 또는 데이터를 공유하는데 프래그먼트가 필요할 수 있습니다. 데이터를 공유하려면 공유된 ViewModel을 생성합니다. ViewModel로 처리할 수 없는 이벤트를 전달해야 할 경우 프래그먼트 안에서 콜백 인터페이스를 정의하고 호스트 액티비티가 이를 구현하도록 요구할 수 있습니다. 액티비티가 인터페이스를 통해 콜백을 수신하면, 필요에 따라 그 정보를 레이아웃 내의 다른 프래그먼트와 공유할 수 있습니다.

예를 들어 어떤 뉴스 애플리케이션은 액티비티 하나에 프래그먼트가 두 개 있습니다. 하나는 기사 목록을 표시하고 다른 하나는 기사 하나를 표시하는 경우, 목록 항목이 선택되면 프래그먼트 A가 액티비티에 알려야 프래그먼트 B에 해당 기사를 표시하라고 알릴 수 있습니다. 이 경우, OnArticleSelectedListener 인터페이스는 프래그먼트 A 내부에 선언됩니다.

```kotlin
public class FragmentA : ListFragment() {
	...
	// Container Activity must implement this interface
	interface OnArticleSelectedListener {
		fun onArticleSelected(articleUri: Uri)
	}
	...
}
```

그러면 프래그먼트를 호스팅하는 액티비티가 OnArticleSelectedListener 인터페이스를 구현하고 onArticleSelected() 를 재정의하여 프래그먼트 A로부터 발생한 이벤트를 프래그먼트 B에 알립니다. 호스트 액티비티가 이 인터페이스를 구현하게 하려면 프래그먼트 A의 onAttach() 콜백 메서드(프래그먼트를 액티비티에 추가할 때 시스템이 호출하는 메서드)가 onArticleSelectedListener의 인스턴스를 생성해야 합니다. 이때 Activity로 전달되는 onAttach()를 형변환하는 방법을 사용합니다.

```kotlin
public class FragmentA : ListFragment() {
	var listener: OnArticleSelectedListener? = null
	...
	override fun onAttach(context: Context) {
		super.onAttach(context)
		listener = context as? OnArticleSelectedListener
		if (listener == null) {
			throw ClassCastException("$context must implement OnArticleSelectedListener")
		}
	}
	...
}
```

액티비티가 인터페이스를 구현하지 않았다면 프래그먼트가 ClassCastException를 발생시킵니다. 성공시에는 mListener 멤버가 액티비티의 OnArticleSelectedListener 구현에 대한 참조를 보유하므로, 프래그먼트 A가 액티비티와 이벤트를 공유할 수 있습니다. 이때 OnArticleSelectedListener 인터페이스가 정의한 메서드를 호출하는 방법을 사용합니다. 예를 들어 프래그먼트 A가 ListFragment의 확장인 경우, 사용자가 목록 항목을 클릭할 때마다 시스템이 프래그먼트 안의 onListItemClick()을 호출하고, 그러면 이것이 onArticleSelected()를 호출하여 해당 이벤틀르 액티비티와 공유합니다.

```kotlin
public class FragmentA : ListFragment() {
	var listener: OnArticleSelectedListener? = null
	...
	override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
		//Append the clicked item's row ID with the content provider Uri
		val noteUri: Uri = ContentUris.withAppendeId(ArticleColumns.CONTENT_URI, id)
		//Send the event and Uri to the host activity
		listener?.onArticleSelected(noteUri)
	}
}
```

onListItemClick()에 전달된 id 매개변수는 클릭한 항목의 행 ID이며, 액티비티(또는 다른 프래그먼트)가 이것을 사용해 애플리케이션의 ContentProvider에서 기사를 가져옵니다.

#### 앱 바에 항목 추가

프래그먼트는 ```onCreateOptionsMenu()```를 구현하여 액티비티의 옵션 메뉴에(결과적으로 앱 바에도) 메뉴 항목을 추가할 수 있습니다.
이 메서드가 호출을 수신하게 하려면, ```setHasOptionsMenu()``` 중에 ```onCreate()```를 호출하여 프래그먼트가 옵션 메뉴에 항목을 추가하고자 한다는 것을 나타내야 합니다. 그렇지 않으면 해당 프래그먼트가 ```onCreateOptionsMenu()```로의 호출을 받지 못하게 됩니다.

그런 다음, 프래그먼트로부터 옵션 메뉴에 추가하는 모든 항목이 기존의 메뉴 항목에 추가됩니다. 메뉴 항목을 선택하면 해당 프래그먼트는 ```onOptionsItemSelected()``` 콜백도 수신하게 됩니다.

또한 ```registerForContextMenu()```를 호출하는 방법으로 프래그먼트 레이아웃에 뷰를 등록하여 컨텍스트 메뉴를 제공할 수도 있습니다. 사용자가 컨텍스트 메뉴를 열면, 해당 프래그먼트는 ```onCreateContextMenu()``` 호출을 수신합니다. 사용자가 항목을 선택하면, 해당 프래그먼트는 ```onContextItemSelected()``` 호출을 수신합니다.

### 프래그먼트 수명 주기 처리

프래그먼트의 수명 주기를 관리하는 것은 액티비티의 수명 주기를 관리하는 것과 매우 비슷합니다. 액티비티와 마찬가지로 프래그먼트는 세 가지 상태로 존재할 수 있습니다.

**재개됨(Resumed)**

프래그먼트가 실행 중인 액티비티에 표시됩니다.

**일시정지됨(Paused)**

다른 액티비티가 포그라운드에 있고 포커스를 갖고 있지만, 이 프래그먼트가 있는 액티비티도 여전히 표시됩니다(포그라운드의 액티비티가 부분적으로 투명하거나 전체 화면을 뒤덮지 않습니다.)

**정지됨(Stopped)**

프래그먼트가 보이지 않습니다. 호스트 액티비티가 정지되었거나 프래그먼트가 액티비티에서 제거되었지만 백 스택에 추가되었습니다.
정지된 프래그먼트도 여전히 표시는 됩니다(모든 상태 및 멤버 정보를 시스템이 보존합니다). 하지만 사용자에게는 더 이상 표시되지 않으며 액티비티를 종료하면 이것도 종료됩니다.

액티비티와 마찬가지로 onSaveInstanceState(Bundle), ViewModel 및 영구 로컬 저장소를 결합하여 구성이 변경되고 프로세스가 종료되더라도 프래그먼트의 UI 상태를 보존할 수 있습니다.

액티비티와 프래그먼트의 수명 주기에서 가장 중대한 차이점은 해당되는 백 스택에 저장되는 방법에 있습니다.
기본적으로 액티비티는 정지되면 시스템에서 관리하는 액티비티의 백 스택에 들어갑니다. 하지만 프래그먼트는 이를 제거하는 트랜잭션에서 ```addToBackStack()```을 호출하여 인스턴슬르 저장하라고 명시적으로 요청할 경우에만 호스트 액티비티에서 관리하는 백 스택으로 들어갑니다.

#### 액티비티 수명 주기와의 조화

프래그먼트가 있는 액티비티의 수명 주기는 해당 프래그먼트의 수명 주기에 직접적인 영향을 미칩니다. 따라서 액티비티에 대한 각 수명 주기 콜백이 각 프래그먼트에 대한 비슷한 콜백을 발생시킵니다. 예를 들어 액티비티가 ```onPause()```를 받으면, 해당 액티비티 내의 각 프래그먼트가 ```onPause()```를 받습니다.

프래그먼트에는 프래그먼트의 UI를 빌드하고 소멸시키는 등의 같은 작업을 수행하기 위해 액티비티와의 고유한 상호작용을 처리하는 몇 가지 수명 주기 콜백이 더 있습니다. 이러한 추가 콜백 메서드는 다음과 같습니다.

- ```onAttach()```
	- 프래그먼트가 액티비티와 연결되어 있었던 경우 호출
- ```onCreateView()```
	- 프래그먼트와 연결된 뷰 계층을 생성하기 위해 호출
- ```onActivityCreated()```
	- 액티비티의 ```onCreate()``` 메서드가 반환할 때 호출
- ```onDesttroyView()```
	- 프래그먼트와 연결된 뷰 계층이 제거되는 중일 때 호출
- ```onDetach()```
	- 프래그먼트가 액티비티와 연결이 끊어지는 중일 때 호출

액티비티가 자신의 ```onCreate()``` 콜백을 받은 경우, 해당 액티비티 안에 있는 프래그먼트는 ```onActivityCreate()```콜백을 받습니다.

액티비티가 재개된 상태에 도달하면 자유자재로 프래그먼트를 액티비티에 추가하거나 액티비티에서 제거해도 됩니다. 따라서 액티비티가 재개된 상태에 있는 동안에만 프래그먼트의 수명 주기를 독립적으로 변경할 수 있습니다. 그러나 액티비티가 재개된 상태를 떠나면 액티비티는 다시 프래그먼트를 그 수명 주기 안으로 넣습니다.