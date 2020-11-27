## 학습 날짜

* 2020-11-17(화) 18:00 ~ 18:50
* 2020-11-17(화) 20:30 ~ 23:30

## 학습 내용

### 토이 프로젝트 주제 선정

- 룰렛 프로그램
- 리스트뷰를 활용한 간단 메모
- Painter

간단한 프로젝트를 선정한다는 점과 추후 기능을 넣기에는 리스트뷰를 활용한 간단 메모가 좋을 것 같아서 메모 어플로 선정함

### 메모 어플 설계

#### 필요 기능

1. 버튼을 눌러 메모 리스트 추가
2. 해당 메모 리스트를 눌러 내용 수정

#### xml

안드로이드에서 레이아웃을 지정하기 위해 사용함

<>로 에워싼 태그 요소와 태그 안에서 '항목명 = 값 형식'을 지정하는 속성으로 이루어짐
< 뒤에 태그의 종류를 지정

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android">
```

여기서 RelativeLayout은 태그의 종류이며, 태그에는 시작 태그와 종료 태그가 있음
위에 기술한 내용이 시작 태그

```xml
</RelativeLayout>
```

위가 종료 태그입니다.

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"></RelativeLayout>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"/>
```

위의 두개는 같은 내용입니다.

출처: <https://djluna.tistory.com/13>

**xml과 코드의 장단점**

xml의 장점
1. 뷰를 재사용하거나 다른 레이아웃에서 가져다 쓰기 쉽다.
2. 뷰가 로직과 분리되어 있어서 장기적으로 고나리하기 편리하다.
3. 미리보기를 통해 어떤 뷰를 나타내는지 미리 파악할 수 있다.

코드로 그릴 때의 장점
1. 로직의 상태에 따라 유동적으로 뷰를 조정할 수 있다.
2. 별도로 리소스 파일을 생성할 필요가 없다.

출처: <https://blog.yena.io/studynote/2019/10/31/Android-View-Speed-Test.html>

#### ListView

ListView는 스크롤 가능한 항목을 나타낼 때 사용되는 뷰 그룹입니다. ListView에 먼저 View를 배치한 다음, 데이터가 저장된 곳에서 데이터를 View의 형식에 맞게 변환하여 가져옵니다.

**레이아웃 정의**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:background="@mipmap/ic_launcher_round"
        android:src="@mipmap/ic_launcher"/>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="10dp"
        android:orientation="vertical"
        android:layout_marginLeft="10dp">
        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="이름"
            android:textSize="30sp"
            android:textStyle="bold"
            android:textColor="@android:color/holo_blue_dark"/>
        <TextView
            android:id="@+id/textView2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="전화번호"
            android:textSize="24sp"
            android:textColor="@android:color/darker_gray"/>
    </LinearLayout>
</LinearLayout>
```

LinearLayout이기에 겹쳐지지 않는 구조를 가지게 됩니다. 첫번째로 이미지가 나오고, 그 다음으로는 텍스트가 위아래로 배치된 형태를 가집니다.

**아이템을 위한 클래스 정의**

목록을 구성할 데이터를 정의합니다.
위의 xml을 참고하면 필요한 데이터는 어떤 이미지를 사용할 것인지, 이름은 무엇인지, 전화번호는 무엇인지가 필요합니다.

```kotlin
public class SingleList (var name: String, var mobile: String, var resId: Int) {
    override fun toString(): String {
        return "SingleItem{name='$name', mobile='$mobile'}"
    }
}
```

**아이템을 위한 뷰 정의**

```kotlin
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class SingleItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var textView: TextView
    var textView2: TextView
    var imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.single_list, this, true)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        imageView = findViewById(R.id.imageView)
    }

    public fun setName(name: String) {
        textView.setText(name)
    }
    public fun setMobile(mobile: String) {
        textView2.setText(mobile)
    }
    public fun setImage(resId: Int) {
        imageView.setImageResource(resId)
    }
}
```

전체 중 부분화면만 구성하기 때문에 처음 정의한 XML 레이아웃을 인플레이션 후 설정해야 합니다.
setter를 통해 정의만 되어 있는 아이템의 이미지와 텍스트에 데이터를 넣을 수 있습니다.

여기서 인플레이터란 XML에 정의된 Resource들을 View의 형태로 반환해 주는 것입니다. 보통 Activity를 만들면 onCreate() 메서드에 추가되어 있는 setContentView(R.layout.activity_main) 메서드와 같은 원리입니다.
이 메서드 또한 activity_main.xml 파일을 View로 만들어서 Activity위에 보여주는 방식입니다.

**Adapter 정의하기**

MainActivity에 데이터 관리 역할을 하는 어댑터 클래스를 만듭니다.
그 안에 각 아이템으로 표시할 뷰를 리턴하는 getView() 메서드를 정의합니다.

```kotlin
class MainActivity : AppCompatActivity() {
    var cnt : Int = 0
    var list = arrayListOf<SingleList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val listView : ListView = findViewById(R.id.mainListView)
        val listAdapter = SingleAdapter(this, list)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            listAdapter.addItem(SingleList("new Text", cnt.toString(), R.drawable.ic_launcher_foreground))
            cnt++
            listView.adapter = listAdapter
        }
    }
	...
    inner class SingleAdapter(val context : Context, val items : ArrayList<SingleList>) : BaseAdapter() {
        override fun getCount() : Int {
            return items.size
        }
        fun addItem(item : SingleList) {
            items.add(item)
        }
        override fun getItem(pos : Int) : SingleList {
            return items[pos]
        }
        override fun getItemId(pos : Int) : Long {
            return pos.toLong();
        }
        override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : SingleItemView? {
            var singleItemView : SingleItemView? = null
            singleItemView = if (convertView == null) {
                SingleItemView(applicationContext)
            } else {
                convertView as SingleItemView?
            }
            val item : SingleList = items.get(pos)
            singleItemView?.setName(item.name)
            singleItemView?.setMobile(item.mobile)
            singleItemView?.setImage(item.resId)
            return singleItemView
        }
    }
}
```

이제 FloatingActionButton을 누르면 정의한 뷰가 순차적으로 나타납니다.

## 출처

- 리스트뷰, 어댑터 기본 예제
	- <https://bite-sized-learning.tistory.com/213>
	- <https://m.blog.naver.com/PostView.nhn?blogId=cosmosjs&logNo=221462146521&proxyReferer=https:%2F%2Fwww.google.com%2F>
	- <https://blog.yena.io/studynote/2017/12/01/Android-Kotlin-ListView.html>
- 커스텀 뷰 사용
	- <http://susemi99.kr/5126/>
	- <https://medium.com/@futureofdev/%EC%BD%94%ED%8B%80%EB%A6%B0-kotlin-customview-774e236ca034>
- 안드로이드 LayoutInflater
	- <https://medium.com/vingle-tech-blog/android-layoutinflater-b6e44c265408>
	- <https://class-programming.tistory.com/22>
- 안드로이드 레이아웃
	- <https://recipes4dev.tistory.com/66>
	- <https://zion830.tistory.com/7>