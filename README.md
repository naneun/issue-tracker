# Issue Tracker

## 프로젝트 멤버
- Android
  - 히데
  - Jung Park

- BE
  - BC
  - Riako

## Ground Rule

  - 주말에 작업을 해야할 사항이 있다면 사전에 양해를 구하자. (당일에 소집하지 않기)
  - 아침 30분 회의 (+ 추가적인 이슈 발생 시 슬랙으로 회의)

## 브랜치 전략

```
 team-03
 | 
 | - develop-Android (개발)
 |     |
 |     | - Android-(# 제외 이슈 번호)
 |  
 | - deploy (배포)
 | - develop-BE (개발)
       |
       | - BE-(# 제외 이슈 번호)
```

## 브랜치 및 이슈 컨벤션

  - 자유롭게 작성
       
## 커밋 메시지 컨벤션

  - feat : 새로운 기능의 추가
  - fix: 버그 수정
  - docs: 문서 수정
  - style: 스타일 관련 기능(코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
  - refactor: 코드 리펙토링
  - test: 테스트 코트, 리펙토링 테스트 코드 추가
  - chore: 빌드 업무 수정, 패키지 매니저 수정 (ex .gitignore 수정 같은 경우)
