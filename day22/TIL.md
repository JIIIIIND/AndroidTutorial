## 학습 날짜

- 2021-01-18

## 학습 내용

### DataBinding

데이터 바인딩은 명령형 방식이 아닌 선언적 형식으로 레이아웃의 UI 구성 요소를 앱의 데이터와 결합할 수 있는 라이브러리입니다.

일반적인 명령형 방식으로 TextView를 뷰 모델로부터 사용자 이름을 참조하여 텍스트를 변경하는 예제입니다.

```kotlin
val textView = findViewById(R.id.sample_text)
textView.setText(viewModel.getUserName())
```

데이터 바인딩을 사용하면 다음과 같이 레이아웃 파일에서 직접 데이터 바인딩 표현식을 사용하여 선언형 프로그래밍 코드를 작성할 수 있습니다.

```xml
<TextView>
	android:text="@{viewModel.userName} />
```

데이터 바인딩을 통해 findViewById 메서드를 호출할 필요가 없어 앱 성능이 향상되고 메모리 누수 및 Null Pointer Exception을 방지할 수 있습니다.

#### 설정 방법

Android 4.0 이상 안드로이드 기기에서 지원합니다. 애플리케이션 모듈의 build.gradle에 다음 내용을 추가합니다.

```xml
android {
	...
	dataBinding {
		enabled = true
	}
}
```

데이터 바인딩의 설정이 끝나면 다음과 같은 기능이 활성화됩니다.
- 구문 강조
- 데이터 바인딩 표현식 오류 검출
- XML 코드 자동 완성
- 빠른 코드 참조

#### 바인딩 클래스 생성

xml 레이아웃 파일에서 가장 상위 레이아웃을 ```<layout>```으로 감싸면 바인딩 클래스가 자동으로 생성됩니다.
생성되는 바인딩 클래스의 이름은 레이아웃 파일의 이름을 기반으로 결정됩니다. xml레이아웃 파일명은 각 단어의 사이를 언더바로 구분하는 스네이크 케이스지만, 바인딩 클래스는 기존 xml 레이아웃 파일명을 파스칼 케이스로 변경한 뒤 접미어 Binding을 붙여서 생성됩니다.

레이아웃에 대한 표현은 ~Binding 클래스로 작성되지만, 실제 비즈니스 로직을 추적 또는 디버깅하려면 ~BindingImpl을 참조해야 합니다.

#### 바인딩 클래스로 바인딩 객체 생성

일반적인 방법으로는 바인딩 클래스의 static 메서드를 이용하는 것입니다.

```kotlin
val binding = ActivityMainBinding.inflate(layoutInflater)
```

바인딩 클래스를 통해 레이아웃을 전개하지 않고 전개 후에 바인딩한다면 다음과 같이 bind() 메서드를 사용합니다.

```kotlin
val binding = ActivityMainBinding.bind(rootView)
```

바인딩 클래스 이름을 미리 알지 못하는 경우는 DataBindingUtil 클래스를 활용할 수 있습니다.

액티비티의 setContentView를 다음과 같이 대체할 수도 있습니다.

```kotlin
val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
```

#### 바인딩 클래스 이름 사용자화 하기

기본적인 바인딩 클래스의 이름을 변경하고 싶다면 ```<data>``` 태그 내에 class 속성을 사용할 수 있습니다.
예를 들어 현재 모듈의 패키지명이 com.charlezz라고 가정하고 com.charlezz.databinding 패키지에 ContactItem 바인딩 클래스를 생성하고 싶다면 다음과 같이 지정할 수 있습니다.

```xml
<data class="ContactItem">
	...
</data>
```

databinding 패키지가 아닌 다른 패키지에 저장하려면 온점을 통해 현재 모듈 내의 패키지에 클래스를 생성할 수 있습니다. com.charlezz.ContactItem 클래스를 만드는 예제는 다음과 같습니다.

```xml
<data class=".ContactItem">
	...
</data>
```

전체 패키지명을 다시 지정할 수도 있는데 com.example 패키지에 ContactItem 바인딩 클래스를 생성한다고 가정하면 다음 예제와 같습니다.

```xml
<data class="com.example.ContactItem">
	...
</data>
```

#### ID로 View 참조하기

바인딩 클래스를 사용하면 findViewById()를 호출할 필요가 없습니다. 바인딩 클래스 내부에서 미리 findViewById()를 호출한 결과를 캐싱해 두기 때문입니다.

```xml
<layout>
	<LinearLayout
		xmlns:android="http://schemas.android.com/apk/res/android">
		android:id="@+id/root"
		android:layout_width="match_parent"
		anrdoid:layout_height="match_parent" >
	
	<TextView
		android:id="@+id/tv"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:text="hello world!" >
</layout>
```

```kotlin
val rootLayout : LinearLayout = binding.root
val textView : TextView = binding.tv
textView.setText("Hello Charles")
```
