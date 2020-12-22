## 학습 날짜

* 2020-12-22(화)

## 학습 내용

### Repository pattern

데이터 출처와 관계 없이 동일 인터페이스로 데이터에 접속할 수 있도록 만드는 것입니다.

viewModel은 필요한 데이터를 Repository에 요청하고, Repository는 적절한 저장소에서 요청받은 데이터를 가져옵니다.
Repository가 추상화되어 있기 대문에 viewModel은 언제나 같은 인터페이스로 데이터를 요청할 수 있으며, 실제로 어떤 데이터를 사용하는지 전혀 알 필요가 없기 때문에 앱 환경에 맞는 적절한 데이터를 사용하기에 편리합니다.

#### 사용 이유

- 데이터 로직을 분리시킬 수 있음
- 중앙 집중처리 방식으로, 언제나 일관된 인터페이스로 데이터를 요청할 수 있음
- 클라이언트가 어떤 데이터를 사용할지 선택할 필요 없음
- 어떤 데이터를 가져올지는 Repository에서 결정하여 적절한 데이터를 제공
- 단위 테스트를 통해 검증이 가능해짐
- 새로운 데이터 로직 코드를 쉽게 추가 가능

### Room Database

Room은 안드로이드 제트팩의 데이터베이스 라이브러리 입니다.
Room은 데이터베이스를 설정하고 구성하는 많은 작업을 처리하며 앱이 일반적인 함수 호출을 사용하여 데이터베이스와 상호 작용할 수 있도록 합니다.
내부적으로 Room은 SQLite 데이터베이스 위에 있는 추상화 계층입니다. Room의 용어와 더 복잡한 쿼리에 대한 쿼리 구문은 SQLite 모델을 따릅니다.

안드로이드에서 데이터는 데이터 클래스로 표현되며 데이터는 함수 호출을 사용하여 액세스 및 수정됩니다. 하지만 데이터베이스에서는 엔터티와 쿼리가 필요합니다.

#### Entity

데이터베이스에 저장할 개체 도는 개념과 속성을 나타냅니다.
테이블을 정의하고 해당 클래스의 각 인스턴스는 테이블의 행을 나타냅니다.
각 속성은 열을 정의합니다. 해당 앱에서 Entity는 야간 수면에 대한 정보를 보유합니다.

#### Query

데이터베이스 테이블 또는 테이블 조합의 데이터 또는 정보에 대한 요청 또는 데이터에 대한 작업을 수행하기 위한 요청입니다. 일반적인 쿼리는 항목 가져오기, 삽입 및 업데이트를 위한 것입니다. 예를 들어 시작 시간별로 정렬 된 기록 된 모든 야간 수면을 쿼리할 수 있습니다.

Room은 kotlin 데이터 클래스에서 SQLite 테이블에 저장할 수 있는 항목으로, 함수 선언에서 SQL 쿼리에 이르기까지 모든 노력을 다합니다.

각각의 Entity를 주석이 달린 데이터 클래스로 정의하고 상호 작용을 주석이 달린 인터페이스, 데이터 액세스 개체로 정의해야 합니다.
Room은 이러한 주석이 추가 된 클래스를 사용하여 데이터베이스에 테이블과 데이터베이스에서 작동하는 쿼리를 만듭니다.

### Sleep Night

#### Step 1: Create the SleepNight entity

```kotlin
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,
        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,
        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1
)
```

필요한 데이터를 Data class로 정의합니다.

class 정의 이전에 @Entity annotation을 추가하고 테이블의 이름을 인자로 넣습니다. 이 값은 옵션이지만 권장됩니다.

고유한 값을 가지는 nightId는 @Primarykey annotation이 추가됩니다. autoGenerate 옵션을 true로 설정하면 Room에서 각각의 Entity에 대해 ID를 생성합니다.

나머지 프로퍼티에는 @ColumnInfo annotation을 추가합니다.

#### Step 2: Create the SleepDatabase DAO

이 작업에서는 데이터 액세스 개체를 정의합니다. 안드로이드에서 DAO는 데이터베이스 삽입, 삭제 및 업데이트를 위한 편리한 방법을 제공합니다.

Room Database를 사용하느 ㄴ경우 코드에서 kotlin 함수를 정의하고 호출하여 데이터베이스를 쿼리합니다. 이러한 Kotlin 함수는 SQL 쿼리에 매핑됩니다. 주석을 사용하여 DAO에서 이러한 매핑을 정의하면  Room이 필요한 코드를 생성합니다.

DAO는 데이터베이스에 액세스하기 위한 사용자 정의 인터페이스를 정의하는 것으로 생각하면 됩니다.

일반적인 데이터베이스 작업을 위해 Room 라이브러리는 @Insert, @Delete, @Update와 같은 annotation을 제공합니다. 다른 모든 것에는 @Query 주석이 있습니다. SQLite에서 지원하는 모든 쿼리를 작성할 수 있습니다.

추가로 Android 스튜디오에서 쿼리를 만들 때 컴파일러는 SQL쿼리에 구문 오류가 있는지 확인합니다.

sleep-tracker의 데이터베이스는 다음을 수행할 수 있어야 합니다.

- **Insert** new nights
- **Update** an existing night to update an end time and a quality rating
- **Get** a specific night based on its key
- **Get all nights**, so you can display them
- **Get the most recent night**
- **Delete** all entries in the database

