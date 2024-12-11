# Tpap
Travel Planning Application for P

## 1. Tpap이란?
- Tpap은 Travel Planning Application for P의 약자
- 여기서 P는 MBTI의 P. 즉, 무계획적이고 즉흥적인 사람들을 뜻함.
- 나는 극P 성향인 사람으로서 여행계획을 대신 세워주는 앱이 있었으면 좋겠다고 생각하여 만들게 되었음.
- (TMI: 본인을 밥 메뉴 정하는 것도 너무 어려워해서 원래는 메뉴 추천 앱을 만들어보려 했음.)
## 2. 시연 영상
[유튜브 바로가기](https://youtu.be/kGbRPcKUof0)

## 3. 설치 및 사용 방법
1. 안드로이드 스튜디오에서 프로젝트를 열고 안드로이드 기기를 연결하거나 가상 디바이스를 설치하여 실행할 수 있음.
2. apk를 다운받아 앱을 설치하여 실행할 수 있음.

## 4. 주요 기능 및 구현
### <기능>

#### 로그인 페이지
- 로그인 페이지에는 비밀번호 옆 show 버튼이 구현되어있다. 비밀번호를 누르고 show 버튼을 누르면 입력한 비밀번호를 보여주고 다시 누르면 숨긴다. (버튼의 이미지도 변경된다.)
  
  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2001.png" width="200"/><img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2002.png" width="200"/>
- 회원가입 버튼은 따로 구현되어 있지 않다.
- 아이디와 비밀번호는 모두 'qwer'이다.

#### 메인 페이지
- 메인 페이지에는 아래 내비게이션바를 통해 홈 페이지와 계정 페이지를 왔다갔다 할 수 있다. (내비게이션바의 현재 띄워진 페이지의 버튼이 진하게 변한다.)

  
  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2003.png" width="200"/><img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2004.png" width="200"/>

- 홈 페이지에는 지금까지 생성한 계획 리스트를 볼 수 있다. 클릭시 해당 계획을 보러 계획 표시 페이지로 이동한다. (만약 생성된 계획이 없다면 비어져있다.)

  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2005.png" width="200"/>
- 계정 페이지에는 로그아웃 버튼이 구현되어있다. 로그인 페이지로 이동한다.

  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2006.png" width="200"/>

- 내비게이션 바의 가운데 플러스 버튼을 눌러 새로운 여행 계획을 생성하러 갈 수 있다. 누를 시 Start Planning 버튼이 떠서 한번 더 확인하고, 배경을 눌러 취소할 수 있다.

  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2007.png" width="200"/>

#### 계획 준비 페이지
- 계획 준비 페이지는 여행지를 선택하는 페이지, 여행기간을 선택하는 페이지, 여행 스타일을 선택하는 페이지, 여행계획의 이름을 정하는 페이지가 있다.
- 여행계획은 최대 일주일까지 선택가능하다.
  
  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2008.png" width="200"/>    <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2009.png" width="200"/>    <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2010.png" width="200"/>    <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2011.png" width="200"/>

- 마지막에 generate plan 버튼을 누르면 로딩 페이지로 넘어가고 AI가 계획을 생성해 준 후 자동으로 계획 표시 페이지로 넘어간다.

#### 계획 표시 페이지
- 계획 표시 페이지에서는 생성된 계획을 볼 수 있다. 날짜별로 계획이 정리되어있고, 기본적으로는 접혀있지만, 눌러서 펼쳐볼 수 있다.

  <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2012.png" width="200"/>    <img src="https://github.com/HyeongSeok03/Tpap/blob/main/Images%20for%20README/Tpap%2013.png" width="200"/>

### <구현>

#### 상태 패턴을 이용한 Fragment 간 전환
- 계획 준비 페이지에서 next 혹은 previous 버튼을 누르면서 계속해서 fragment 변경을 하는데, 이때 상태 패턴을 이용해서 코드의 가독성과 유지보수성을 향상시켰다.
- 아래는 베이스 상태 인터페이스 코드. 이것을 구현(implement)한 클래스들이 계획 준비 페이지 내에서 인스턴스화되어 사용됨.
```java
public interface BaseState {
    BaseState nextState();
    BaseState previousState();
    void updateUI();
}
```

#### 예외 처리
- 앱 시용 시의 예외 처리에 많은 신경을 썼다.
- 계획 준비 페이지에서 만약 여행지나 여행날짜 등을 선택하지 않았다면 다음 페이지로 넘어갈 수 없다.
- 오늘 이전의 날짜는 선택할 수 없고, 여행 종료일을 선택한 후 다시 여행 시작일을 선택하면 여행 종료일은 초기화된다.

#### 별도의 thread를 이용하여 로딩 페이지 구현
- ChatGPT에게 계획서를 보내어 답변이 돌아올 때까지 상당한 시간이 소요됨. (약 10~20초)
- 이때 앱이 멈추게되는데, 이것을 해결하기위해 별도의 thread에서 해당 작업을 실행하게 만들고, 메인 thread에서는 로딩 애니메이션 ui가 돌아가게 만듬.

#### Retrofit2를 이용한 ChatGPT API 네트워킹
- 통신에 Retrofit2를 사용한 이유는 두가지 정도가 있다.
  1. 통신에 사용되는 라이브러리들 중 트렌드는 점점 retrofit2를 사용하는 분위기. 즉, 나중에 현업에서 사용하게 될 가능성이 가장 높다고 판단하였다.
  2. okHttp보다 성능적으로 뛰어나다.
- GPT에게 돌아오는 답변이 가장 중요했다. 왜냐하면 답변은 text형식으로 오는데, 그 답변을 그냥 띄워주는 것이 아닌 가공 후 정리하여 표시하는 것이 나의 프로젝트에서 가장 중요한 부분이었기 때문이다. 만약 그냥 text를 띄워준다면 그냥 GPT에게 바로 질문하지 나의 앱을 사용할 필요가 없지 않은가?
  따라서, GPT의 답변을 항상 일관성있게, 규칙을 가지고 답변을 하게 만들기 위해, 요청 프롬프트를 잘 만드는 것이 가장 중요했다. 그것을 위해 나는 질문 프롬프트에 답변 예시(형식)를 넣어 답변이 가공 가능한 형태로 오게 만들었다. 아래는 답변의 format이다.
```java
Your answer format: "Destination*Paris*Start Date*2024-12-14*End Date*2024-12-20*Duration*7*Plan Start*SAT 11/26*Morning*[GPT's plan]*Lunch*[Meal Recommendation]*Afternoon*[GPT's plan]*Dinner*[Meal Recommendation]*Night*[GPT's plan]*SUN 11/27*Morning*[GPT's plan]*Lunch*[Meal Recommendation]*Afternoon*[GPT's plan]*Dinner*[Meal Recommendation]*Night*[GPT's plan]* …"
```
- 위 답변을 *별로 쪼개어 데이터로 저장하여 사용하였다.

#### .gitignore 파일을 이용한 API 키 보안
- 개발 도중 gpt와의 통신 기능을 구현완료하고 깃에 push를 할때 오류가 났다. 이유를 보니 push 내용 중 api 키를 포함하였기 때문이라고 하였다. google map api 키를 재발급 받고, chatGPT api 키를 같이 .gitignore 파일에 정의된 local.properties 파일에 저장하고, 거기서 데이터를 불러오는 형식으로 하여 api 키를 숨기는 방법을 알게되었다. 

## 5. 라이센스
기본적으로 MIT License를 따른다.

## 6. 참고문헌

[안드로이드 스튜디오 배포용, 테스트용 APK 추출하기](learn2you.tistory.com)
