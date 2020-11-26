## 학습 날짜

* 2020-11-24

## 학습 내용

### 애플리케이션 기본 항목

각 Android ㅇ배은 자체적인 보안 샌드박스에 속하며, 이는 다음과 같은 Android 보안 기능으로 보호됩니다.
- Android 운영체제는 멀티유저 Linux 시스템으로, 여기서 각 앱은 각기 다른 사용자와 같습니다.
- 기본적으로 시스템이 각 앱에 고유한 Linjux ID를 할당합니다(이 ID는 시스템만 사용할 수 있으며 앱에서는 인식하지 못함)
- 시스템은 앱 안의 모든 파일에 대해 권한을 설정하여 해당 앱에 할당된 사용자 ID만 이에 액세스할 수 있도록 함
- 각 프로세스에는 자체적인 가상 머신이 있고, 그렇기 때문에 한 앱의 코드가 다른 앱과는 격리된 상태로 실행
- 기본적으로 모든 앱이 앱 자체의 Linux 프로세스에서 실행됩니다. 안드로이드 시스템은 앱의 구성 요소 중 어느 하나라도 실행해야 하는 경우 프로세스를 시작하고, 더 이상 필요 없거나 시스템이 다른 앱을 위해 메모리를 복구해야 하는 경우 해당 프로세스를 종료합니다.

Android 시스템은 이런 방식으로 최소 권한의 원리를 구현합니다. 다시 말해, 각 앱은 기본적으로 자신의 작업을 수행하기 위해 필요한 구성 요소에만 액세스 권한을 가지고 그 이상은 허용되지 않습니다. 이렇게 하면 대단히 안전한 환경이 구성되어 앱이 시스템에서 권한을 부여받지 못한 부분에는 액세스할 수 없게 됩니다. 그러나 앱이 다른 앱과 데이터를 공유하고 시스템 서비스에 액세스하는 방법은 여러 가지가 있습니다.

- 두 개의 앱이 같은 Linux 사용자 ID를 공유하도록 설정할 수도 있습니다. 이 경우 두 앱은 서로 파일에 액세스할 수 있게 됩니다. 시스템 리소스를 절약하기 위해 사용자 ID가 동일한 앱들이 같은 Linux 프로세스에서 실행되고 같은 VM을 공유하도록 설정할 수도 있습니다. 또한 이러한 앱은 같은 인증서로 서명해야 합니다.
- 앱은 사용자의 연락처, SMS 메시지, 마운트 가능한 저장소, 카메라, 블루투스를 비롯한 여러 가지 기기 데이터에 액세스할 권한을 요청할 수 있습니다. 사용자는 이러한 권한을 명시적으로 부여해야 합니다.

### 앱 구성 요소

앱 구성 요소는 Android 앱의 필수적인 기본 구성 요소입니다. 각 구성 요소는 시스템이나 사용자가 앱에 들어올 수 있는 진입점입니다. 다른 구성 요소에 종속되는 구성 요소도 있습니다.

- 액티비티
- 서비스
- Broadcast Receiver
- 콘텐츠 제공자

각 유형은 뚜렷한 목적을 수행하고 각자 나름의 수명 주기가 있어 구성 요소의 생성 및 소멸 방식을 정의합니다.

#### 액티비티

액티비티는 사용자와 상호작용하기 위한 진입점입니다. 이것은 사용자 인터페이스를 포함한 화면 하나를 나타냅니다. 여러 액티비티가 함께 작동하여 해당 이메일 앱에서 짜임새 있는 사용자 환경을 구성하는 것은 사실이지만, 각자 서로 독립되어 있습니다. 액티비티는 다음과 같이 시스템과 주요 상호작용을 돕습니다.
- 사용자가 현재 관심을 가지고 있는 사항(화면에 표시된 것)을 추적하여 액티비티를 호스팅하는 프로세스를 시스템에서 계속 실행하도록 합니다.
- 이전에 사용한 프로세스에 사용자가 다시 찾을 만한 액티비티가 있다는 것을 알고, 해당 프로세스를 유지하는 데 더 높은 우선 순위를 부여합니다.
- 앱이 프로세스를 종료하도록 도와서 이전 상태가 복원되는 동시에 사용자가 액티비티로 돌아갈 수 있게 합니다.
- 앱이 서로 사용자 플로우를 구현하고 시스템이 이러한 플로우를 조정하기 위한 수단을 제공합니다.

액티비티 하나를 Activity 클래스의 하위 클래스로 구현합니다.

#### 서비스

서비스는 여러 가지 이유로 백그라운드에서 앱을 계속 실행하기 위한 다목적 진입점입니다. 이는 백그라운드에서 실행되는 구성 요소로, 오랫동안 실행되는 작업을 수행하거나 원격 프로세스를 위한 작업을 수행합니다. 서비스는 사용자 인터페이스를 제공하지 않습니다. 예를 들어 사용자와 액티비티 간의 상호작용을 차단하지 않고 네트워크를 통해 데이터를 가져올 수도 있습니다. 다른 구성 요소가 서비스를 시작한 다음 실행되도록 두거나 자신에게 바인딩하여 상호작용하게 할 수도 있습니다.

시스템에 앱 관리 방법을 지시하는 것은 서로 매우 뚜렷하게 구분되는 두 가지 의미 체계 서비스입니다. 시작된 서비스는 작업이 완료될 때까지 해당 서비스를 계속 실행하라고 시스템에 지시합니다.

- 음악 재생은 사용자가 바로 인식할 수 있는 작업입니다. 따라서 앱은 사용자에게 이와 관련된 알림을 보내고 음악 재생을 포그라운드로 옮기라고 시스템에 지시합니다. 이 경우, 시스템은 이 서비스의 프로세스가 계속 실행되도록 많은 노력을 기울여야 합니다. 이 서비스가 사라지면 사용자가 불만을 느낄 것이기 때문입니다.
- 정기적인 백그라운드 서비스는 사용자가 실행되고 있다고 직접 인식할 수 없는 작업이므로 시스템은 좀 더 자유롭게 프로세스를 관리할 수 있습니다. 사용자와 좀 더 직접적인 관련이 있는 작업에 RAM이 필요할 경우 이 서비스를 종료할 수도 있습니다.

바인딩된 서비스는 다른 앱에서 서비스를 사용하고 싶다는 의향을 표현했기 때문에 실행됩니다. 이는 기본적으로 서비스가 다른 프로세스에 API를 제공하는 것입니다. 따라서 시스템은 프로세스 사이에 종속성이 있는지 알게 됩니다. 프로세스 A가 프로세스 B의 서비스에 바인딩되어 있을 경우, 시스템은 프로세스 A를 위해 프로세스 B(및 그 서비스)를 실행해야 한다는 것을 인식하게 됩니다. 또한 사용자가 프로세스 A에 관심을 기울이고 있다면 시스템에서 프로세스 B도 사용자가 관심을 기울이는 것처럼 취급해야 합니다. 서비스는 유연하기 때문에 각종 고차원적 시스템 개념의 매우 유용한 기본 구성 요소로 사용되었습니다. 라이브 배경화면, 알림 리스너, 화면 보호기, 입력 메서드, 접근성 서비스 및 여러 가지 기타 핵심 서비스 기능이 모두 애플리케이션에서 구현하고 시스템에서 애플리케이션을 실행할 때 바인딩하는 서비스로 빌드됩니다.

서비스는 Service 하위 클래스로 구현됩니다.

#### Broadcast Receiver

Broadcast Receiver는 시스템이 정기적인 사용자 플로우 밖에서 이벤트를 앱에 전달하도록 지원하는 구성 요소로, 앱이 시스템 전체의 브로드캐스트 알림에 응답할 수 있게 합니다. Broadcast Receiver도 앱으로 들어갈 수 있는 또 다른 명확한 진입점이기 때문에 현재 실행되지 않은 앱에도 시스템이 브로드캐스트를 전달할 수 있습니다.

대다수의 브로드캐스트는 시스템에서 발생합니다. 예를 들어 화면이 꺼졌거나 배터리가 부족하거나 사진을 캡처했다고 알리는 브로드캐스트가 대표적입니다. 앱도 브로드캐스트를 시작할 수 있습니다. 예를 들어 다른 앱에 일부 데이터가 기기에 다운로드되었고 이를 사용할 수 있다는 것을 알리는데 사용합니다. Broadcast Receiver는 사용자 인터페이스를 표시하지 않지만, 상태 표시줄 알림을 생성하여 사용자에게 브로드캐스트 이벤트가 발생했다고 알릴 수 있습니다. 다만 Broadcast Receiver는 그저 다른 구성 요소로의 게이트웨이인 경우가 더 보편적이고, 극소량의 작업만 수행하도록 만들어진 경우가 많습니다. 예를 들어 JobService를 예약하여 시작하여 JobScheduler가 포함된 이벤트를 기초로 어떤 작업을 수행하게 할 수 있습니다.

Broadcast Receiver는 BroadcastReceiver의 하위 클래스로 구현되며 각 브로드캐스트는 Intent 객체로 전달됩니다.

#### 콘텐츠 제공자

콘텐츠 제공자는 파일 시스템, SQLite 데이터베이스, 웹상이나 앱이 액세스할 수 있는 다른 모든 영구 저장 위치에 저장 가능한 앱 데이터의 공유형 집합을 관리합니다. 다른 앱은 콘텐츠 제공자를 통해 해당 데이터를 쿼리하거나, 콘텐츠 제공자가 허용할 경우에는 수정도 가능합니다.

콘텐츠 제공자르 레이터 베이스에 대한 추상화로 생각하기 쉽지만 시스템 설계 관점에서 볼 때 핵심 목적이 서로 다릅니다. 시스템의 경우 콘텐츠 제공자는 URI 구성표로 식별되고 이름이 지정된 데이터 항목을 게시할 목적으로 앱에 진입하기 위한 입구입니다. 따라서 앱에서 URI 네임스페이스에 넣을 데이털르 매핑할 방식을 결정하고, 해당 URI를 다른 엔터티에 전달할 수 있습니다. 이를 전달받은 엔터티는 URI를 사용하여 데이터에 액세스합니다. 시스템이 이렇게 할 수 있는 데에는 앱 관리에 몇 가지 특별한 점이 있기 때문입니다.
- URI를 할당하더라도 앱을 계속 실행할 필요가 없으므로 URI를 소유한 앱이 종료된 후에도 URI를 유지할 수 있습니다. 시스템은 URI를 소유한 앱이 해당 URI에서 앱의 데이터를 검색할 때만 실행되도록 하면 됩니다.
- 이 URI는 중요하고 조밀한 보안 모델을 제공합니다. 예를 들어 앱은 클립보드에 있는 이미지에 URI를 할당하고 콘텐츠 제공자가 검색하도록 하여, 다른 앱이 자유롭게 이미지에 액세스하지 못하게 막을 수 있습니다. 두 번째 앱이 클립보드에서 해당 URI에 액세스하려고 시도하면 시스템에서는 임시 URI 권한을 부여하여 그 앱이 데이터에 액세스하도록 허용할 수 있습니다. 따라서 두 번째 앱에서는 URI 뒤에 있는 데이터 외에 다른 것에는 액세스할 수 없습니다.