## 학습 날짜

* 2020-12-07(월)

## 학습 내용

### Starter-app

GuessTheWord 앱의 문제점 찾기
- 디바이스의 회전, 재시작 등에 대해 상태가 저장되지 않음
- end game 버튼을 눌렀을 때 반응이 없음

이러한 문제점들은 app architecture components를 공부하면 해결할 수 있음

안드로이드 앱의 아키텍처는 MVVM 아키텍쳐 패턴과 유사함

#### 해결 방법

**Create GameViewModel**

ViewModel 클래스는 ui와 관련된 데이터를 저장하고 관리하기 위해 설계되었음

각각의 뷰모델은 하나의 프래그먼트에 연관됩니다.

```kotlin
class GameViewModel : ViewModel() {
    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}
```

다음은 GameViewModel을 game fragment에 연관시킵니다.

GameFragment 클래스 내부에 GameViewModel 필드를 선언

```kotlin
private lateinit var viewModel: GameViewModel
```

마지막으로 뷰모델을 초기화 합니다.

주의할 점은 언제나 ViewModelProvider를 사용하여 뷰모델 객체를 생성해야 합니다.

```kotlin
viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
```

이후 로그를 찍어보면 GameViewModel은 한번만 생성됩니다. 프래그먼트는 제거되고 재생성되므로 ViewModelProvider.get은 매번 호출됩니다.

뷰모델은 설정이 변할 때에도 생존하기 때문에 데이터가 보존됩니다.

**Move Data field and data processing to the ViewModel**

- word, score wordList data field를 옮깁니다.
	- word와 score는 private이 아니도록 변경합니다.
	- binding 변수에는 뷰의 레퍼런스들이 포함되었기에 옮기지 않도록 주의합니다.
- resetList()와 nextWord()도 옮깁니다.
	- 해당 함수는 화면에 보여질 단어를 결정합니다.
- GameViewModel의 init블록에 onCreateView에서 호출하던 resetList()와 nextWord()를 옮깁니다.

**Update references to click handlers and data fields in GameFragment**

- onSkip과 onCorrect는 viewModel의 onSkip과 onCorrect를 호출하도록 변경하고 내부에서 updateWordText와 updateScoreText를 호출하도록 합니다.
- nextWord를 viewModel로 이동하여 fragment에서 접근할 수 없기 때문입니다.
- GameFragment에서 score와 word 변수는 viewModel의 변수를 사용합니다. 이유는 마찬가지로 해당 변수들이 viewModel에 있기 때문입니다.
- GameViewModel에서 nextWord()내부의 update함수들을 제거합니다. fragment내부의 함수들이기에 접근할 수 없으며, 해당 함수에서 처리하도록 변경되었습니다.

**End Game 버튼 이벤트**

이전에 사용한 네비게이션을 통해 버튼을 누르면 ScoreFragment로 이동하게 할 수 있습니다.

```kotlin
	override fun onCreateView(...) {
		...
		binding.endGameButton.setOnClickListener { onEndGame() }
		...
	}

    private fun onEndGame() {
        gameFinished()
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score
        NavHostFragment.findNavController(this).navigate(action)
    }
```

score는 네비게이션의 safe args 기능을 사용합니다.

하지만 단순히 연결해서는 score가 제대로 보이지 않습니다.

**ViewModelFactory**

위에서 뷰모델을 사용하여 score를 잡아두고 scoreFragment에서 보여주기를 원했습니다.

이 작업을 위해서는 score 값을 팩토리 메서드 패턴을 사용한 ViewModel의 초기화 단계에 넘겨주어야 합니다.

```kotlin
class ScoreViewModel(finalScore: Int) : ViewModel() {
    var score = finalScore
    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }
}
```

다음과 같이 ScoreViewModel을 작성합니다.

```kotlin
class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

이후 다음과 같은 ViewModelFactory를 작성합니다.

```create(modelClass: Class<T>): T {...}```에서는 modelClass의 인자 값이 ScoreViewModel이라면 ScoreViewModel(finalScore)를 반환하고 아니라면 예외를 던집니다.

```kotlin
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )
        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(ScoreViewModel::class.java)

        binding.scoreText.text = viewModel.score.toString()
        return binding.root
    }
}
```

ScoreFragment에서는 다음과 같이 ViewModelFactory를 만들어주고 해당 변수를 통해 viewModel을 생성합니다.

