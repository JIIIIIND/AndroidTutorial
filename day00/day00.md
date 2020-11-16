## 학습 날짜

* 2020-11-16 09:00 ~ 11:30

## 학습 내용

### 중요 파일

#### app > java > <package name> > MainActivity

기본 활동으로, 앱의 진입점입니다. 앱을 빌드하고 실행하면 시스템에서 이 Activity의 인스턴스를 실행하고 레이아웃을 로드합니다.

#### app > res > layout > activity_main.xml

이 XML 파일은 활동의 사용자 인터페이스(UI) 레이아웃을 정의합니다. 이 파일에는 "Hello, World!"라는 텍스트가 있는 TextView 요소가 포함되어 있습니다.

#### app > manifests > AndroidManifest.xml

매니페스트 파일은 앱의 기본 특성을 설명하고 앱의 각 구성요소를 정의합니다.

#### Gradle Scripts > build.gradle

이 이름을 가진 파일은 두 개입니다. 하나는 프로젝트용으로 'Project: App name'이고 다른 하나는 앱 모듈용으로 'Module: app'입니다. 각 모듈에는 자체 build.gradle 파일이 있지만 이 프로젝트에는 현재 모듈이 하나만 있습니다. 각 모듈의 build.file을 사용하여 Gradle 플러그인이 앱을 빌드하는 방법을 제어합니다.

### 앱 실행

#### 실제 기기 사용

1. USB케이블로 기기와 PC 연결
	- Windows에서 개발한다면 적합한 USB 드라이버를 설치해야 할 수 있음
2. 다음 단계를 통해 USB Debugging 설정
	- Settings 앱을 실행
	- 기기가 android 8.0 이상이라면 System선택
	- 하단에서 About Phone을 선택
	- Build number를 7번 탭
	- 이전 화면의 하단에서 Developer options를 선택
	- 아래쪽에서 USB Debugging을 찾아 설정

이후 실행/디버그 구성 드롭다운 메뉴에서 앱을 선택하고 대상 기기 메뉴에서 기기를 선택하면 됩니다.

#### 에뮬레이터에서 실행

다음과 같이 앱을 에뮬레이터에서 실행합니다.

1. Android Virtual Device를 생성
2. 툴바의 실행/디버그 구성 드롭다운 메뉴에서 앱 선택
3. 대상 기기 드롭다운 메뉴에서 해당 AVD 선택