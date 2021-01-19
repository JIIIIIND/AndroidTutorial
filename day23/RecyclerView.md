## 학습 날짜

- 2021-01-19

## 학습 내용

### RecyclerView

항목이 화면 밖으로 스크롤되면 항목의 보기가 재활용됩니다. 즉, 항목이 화면으로 스크롤 될 때 새 콘텐츠로 채워집니다. 이 동작은 많은 처리 시간을 절약하고 목록을 부드럽게 스크롤하는 데 도움이 됩니다.

항목이 변경되면 전체 목록을 다시 그리는 대신 해당 항목을 업데이트 할 수 있습니다. 이 동작은 복잡한 항목의 긴 목록을 표시할 때 엄청난 효율성의 향상을 줍니다.

#### Adapter pattern

RecyclerView는 어댑터를 사용하여 앱이 데이터를 저장하고 처리하는 방법을 변경하지 않고 앱 데이터를 RecyclerView가 표시할 수 있는 것으로 변환합니다. 이번 튜토리얼의 앱의 경우, ViewModel을 변경하지 않고 Room Database의 데이터를 RecyclerView가 표시하는 방법을 알고 있는 항목으로 조정하는 어댑터 빌드입니다.

#### RecyclerView 구현

RecyclerView에 데이터를 표시하려면 다음 부분이 필요합니다.
- 화면에 보여 줄 데이터
- 레이아웃에 정의 된 RecyclerView 인스턴스
	- 뷰의 컨테이너 역할을 함
- 하나의 데이터 항목에 대한 레이아웃
	- 모든 목록 항목이 동일하게 보이면 모든 항목에 대해 동일한 레이아웃을 사용할 수 있지만 필수는 아님
	- 항목 레이아웃은 fragment's 레이아웃과 별도로 생성해야 합니다.
	- 그렇게 해야지 한번에 하나의 항목을 만들고 데이터를 채울 수 있습니다.
	- 뷰홀더(ViewHolder)
		- 뷰홀더는 ViewHolder 클래스를 확장합니다.
		- 항목의 레이아웃에서 하나의 항목을 표시하기 위한 보기 정보가 포함됩니다.
		- 뷰홀더는 RecyclerView가 화면에서 뷰를 효율적으로 이동하는 데 사용하는 정보도 추가합니다.
	- 어댑터
		- 어댑터는 데이터를 RecyclerView에 연결합니다.
		- ViewHolder에 표시 될 수 있도록 데이터를 조정합니다.
		- RecyclerView는 어댑터를 사용하여 화면에 데이터를 표시하는 방법을 파악합니다.

1. Add RecyclerView with LayoutManager

RecyclerView를 가지는 레이아웃을 작성합니다.

```xml
<androidx.recyclerview.widget.RecyclerView
            android:id="@+id/sleep_list"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toTopOf="@+id/clear_button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/stop_button"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
```

layoutManger로 LinearLayoutManager를 넣었는데 다른 Manager를 넣어도 됩니다.

2. Create the list item layout and text view holder

아이템의 레이아웃을 만들어줍니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:textSize="24sp"
    android:paddingStart="16dp"
    android:paddingEnd="16dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

다음과 같이 TextItem을 담아두는 ViewHolder를 정의합니다.

```kotlin
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
```

3. Create SleepNightAdapter

RecyclerView 구현에 가장 중심이 되는 Adapter입니다.

Adapter는 ViewHolder를 만들고 RecyclerView가 보여 줄 데이터로 채워줍니다.

```kotlin
class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}
```

Adapter는 getItemCount()를 통해 화면에 보여 줄 Data의 리스트인 data의 사이즈를 얻을 수 있습니다.
RecyclerView는 얼마나 많은 아이템이 어댑터를 통해 보여질지 알아야 합니다.

OnBindViewHolder 함수는 RecyclerView에 의해 호출되어 지정된 위치에 하나의 목록 항목에 대한 데이터를 표시합니다.
따라서 OnBindViewHolder 메서드는 뷰 홀더와 바인딩 할 데이터의 위치라는 두 가지 인수를 사용합니다.
해당 튜토리얼에서 홀더는 TextItemViewHolder이고 위치는 목록의 위치입니다.

뷰홀더에는 textView라는 속성이 있습니다. OnBindViewHolder() 내에서 textView의 텍스트를 수면 품질로 설정합니다.
이 코드에서는 숫자 목록만 표현하지만 어댑터가 데이터를 ViewHolder와 화면으로 가져 오는 방법을 확인할 수 있습니다.

onCreateViewHolder는 두 개의 매개변수를 취하고 ViewHolder를 반환합니다. ViewHolder를 보유하는 ViewGroup인 부모 매개 변수는 항상 RecyclerView입니다.
viewType 매개변수는 동일한 RecyclerView에 여러 보기가 있을 때 사용됩니다. 예를 들어 텍스트, 이미지 및 비디오 목록을 모두 동일한 RecyclerView에 넣는 경우 onCreateViewHolder() 함수는 사용할 보기 유형을 알아야 합니다.

LayoutInflater는 xml 레이아웃에서 뷰를 만드는 방법을 알고 있습니다.
Context에는 뷰를 올바르게 확장하는 방법에 대한 정보가 포함되어 있습니다.
RecyclerView를 위한 어댑터에서는 항상 상위 ViewGroup인 RecyclerView의 Context에서 전달합니다.

RecyclerView는 데이터에 대해 아무것도 모르기 때문에 어댑터는 데이터가 변경된 시기를 RecyclerView에 알려야 합니다.
어댑터가 제공하는 ViewHolder에 대해서만 알고 있습니다.

4. Tell RecyclerView about the Adapter

RecyclerView는 ViewHolder를 가져 오는 데 사용할 어댑터에 대해 알아야 합니다.
Fragment의 onCreateView() 내부에서 어댑터를 생성합니다.
ViewModel의 모델이 생성된 이후에 작성합니다.

```kotlin
val adapter = SleepNightAdapter()
binding.sleepList.adapter = adapter
```

바인딩 개체에 대한 참조를 가져온 후 어댑터를 RecyclerView와 연결합니다.

5. Get data into the adapter

```kotlin
sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
```

nights는 LiveData List이기 때문에 변화를 감지할 수 있습니다. 관찰자 내에서 null이 아닌 값을 얻을 때마다 값을 어댑터의 데이터에 할당합니다.
