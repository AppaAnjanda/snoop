# 기웃기웃
---
### 베이스 코드 설명
- 탑 바 생성하고 싶으면
  - common/topbar/component에 원하는 배치의 탑바를 만들어 줍시다.
  - common/topbar/utils/getTopbar.kt 파일 메소드에 (1)에서 만든 탑바를 배치시켜 줍시다.
  - 만들어 놓은거 있으니 보면서 하면 알기 쉬울겁니다.

- 새로운 화면을 만들었다. Nav에 추가해야한다
  - navigation/Router에 새로운 라우터와 타이틀을 추가해 줍시다.
  - navigation/NavItems 하단에 똑같은 형식으로 object화 해줍시다.
  - navigation/SnoopNavHost에 composable로 화면을 추가해 줍시다.
  - 화면간 이동은 LaunchEffect를 잘 활용하세요 (당신의 몫)