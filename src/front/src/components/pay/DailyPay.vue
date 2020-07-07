<template>
  <div class="daily">
    <div class="subject">
      <h3>일급 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="hourlyWage"/>
      </div>

      <div class="startTime">
        <label for="startTime">시작 시간</label>
        <datetime type="time" id="startTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="startTime">
        </datetime>
      </div>

      <div class="endTime">
        <label for="endTime">종료 시간</label>
        <datetime type="time" id="endTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="endTime">
        </datetime>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calWeeklyPay(hourlyWage, startTime, endTime)" class="btn btn-success">계산하기</button>
    </div>


    <div class="resultArea" v-show="result">
      <div class="text">
        <div class="cal">
          <div class="title">
            <h5>하루 총 근무시간</h5>
            <h5>주간 근무</h5>
            <h5>야간 근무</h5>
          </div>
          <div class="content">
            <h5>{{ dailyTotalTime }}시간</h5>
            <h5>{{ dayTime }}시간 × {{ _hourlyWage }}원 = <strong style="color: #298861;">{{ dayPay }}원</strong></h5>
            <h5>{{ nightTime }}시간 × ({{ _hourlyWage }}원 × 1.5) = <strong style="color: #298861;">(+) {{ nightPay }}원</strong></h5>
          </div>
        </div>
        <hr style="border: solid 1px gray; margin-top: 0">
        <h4>총 일급은 <b style="color: #e16441;">{{ dailyPay }}원</b>입니다.</h4>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DailyPay",
    data() {
      return {
        result: false,
        hourlyWage: null,
        _hourlyWage: null,
        startTime: "",
        endTime: "",
        dayTime: 0,
        dayPay: 0,
        nightTime: 0,
        nightPay:0,
        dailyTotalTime: 0,
        dailyPay: 0
      }
    },
    methods: {
      calWeeklyPay(hourlyWage, startTime, endTime) {
        this.dayTime = 0;
        this.nightTime = 0;

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        const formatStartTime = startTime.substring(11, 13);   // 2020-06-26T09:00:00.000+09:00 -> 09
        const formatEndTime = endTime.substring(11, 13);   // 12

        const workStartTime = parseInt(formatStartTime);   // 9
        const workEndTime = parseInt(formatEndTime);   // 12

        var timeList = new Array(24);
        for(var i=0; i<=23; i++) {
          timeList[i] = Boolean(false);
        }

        if(workStartTime < workEndTime) {   // 근무 시간이 하루 안에 시작 및 종료
          for(var i=workStartTime; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        } else {   // 근무 시간이 자정을 넘어감
          for(var i=workStartTime; i<=23; i++) {
            timeList[i] = Boolean(true);
          }
          for(var i=0; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        }


        var timeMap = new Map();
        for(var i=0; i<=23; i++) {
          if(timeList[i] == Boolean(true)) {
            if(i>=6 && i<=21) {
              timeMap.set(i, hourlyWage);   // 주간 기본 시급
              this.dayTime++;
            } else {
              timeMap.set(i, (hourlyWage*1.5));   // 야간 수당
              this.nightTime++;
            }
          }
        }

        var sum=0;
        timeMap.forEach(function (value) {
          sum += value;
        });
        this.dailyTotalTime = (this.dayTime + this.nightTime);
        this.dayPay = this.dayTime * hourlyWage;
        this.nightPay = this.nightTime * (hourlyWage * 1.5);
        this._hourlyWage = hourlyWage.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.dayPay = this.dayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.nightPay = this.nightPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.dailyPay = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.result = true;
      },
    }
  }
</script>

<style lang="scss" scoped>

  .daily {
    margin-top: 100px;

    .subject {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .inputForm {

      justify-content: center;   // 화면 중앙
      text-align: center;
      max-width: 500px;
      margin: auto;

      .hourlyWageForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

      }

      .startTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }

      .endTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }
    }

    .btnArea {
      padding-top: 50px;
      padding-bottom: 50px;
      display: flex;

      width: 100px;
      justify-content: center;
      font-size: 18px;
      margin: 0 auto;
    }

    .resultArea {
      background-color: lightgrey;
      font-size: large;
      width: 100vw;
      margin: auto;
      align-items: center;
      justify-content: space-between;

      .text {
        margin: auto;
        padding: 100px 500px;

        .cal {
          display: flex;

          .title {
            width: 500px;
            text-align: left;

            h5 {
              margin-bottom: 15px;
            }
          }

          .content {
            width: 500px;
            text-align: right;

            h5 {
              margin-bottom: 15px;
            }
          }
        }

        h4 {
          text-align: right;
        }
      }
    }
  }
</style>
