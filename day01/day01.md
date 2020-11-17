## 학습 날짜

* 2020-11-17 18:00 ~ 18:50

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



## 출처

- 리스트뷰, 어댑터 기본 예제
	- <https://bite-sized-learning.tistory.com/213>
- 커스텀 뷰 사용
	- <http://susemi99.kr/5126/>
	- <https://medium.com/@futureofdev/%EC%BD%94%ED%8B%80%EB%A6%B0-kotlin-customview-774e236ca034>
- 안드로이드 LayoutInflater
	- <https://medium.com/vingle-tech-blog/android-layoutinflater-b6e44c265408>
