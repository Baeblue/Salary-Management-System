<template>
  <div class="monthly">
    <div class="subject">
      <h3>월급 계산하기</h3>
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
                  :input-style="'width: 350px; border: transparent'"
                  class="form-control"
                  v-model="startTime">
        </datetime>
      </div>

      <div class="endTime">
        <label for="endTime">종료 시간</label>
        <datetime type="time" id="endTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 350px; border: transparent'"
                  class="form-control"
                  v-model="endTime">
        </datetime>
      </div>

      <div class="workingDays">
        <label>월 근무 일수</label>
        <b-dropdown variant="outline-info"
          id="workingDays" class="m-2" required
          :text="selectedDays"
          v-model="days"
        >
          <b-dropdown-item
            v-for="days in daysList" :key="days.id"
            @click="selectDays(days)"
          >
            {{ days }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calMonthlyPay(hourlyWage, startTime, endTime, days)" class="btn btn-success">계산하기</button>
    </div>

    <div class="resultArea" v-show="result">
      <div class="text">
        <div class="cal">
          <div class="title">
            <h5>월 총 근무 시간</h5>
            <h5>기본 수당</h5>
            <h5>예상 주휴 수당</h5>
          </div>
          <div class="content">
            <h5>하루 {{ dailyTotalTime }}시간 × {{ days }}일 = 총 {{ monthlyTotalTime }}시간</h5>
            <h5>일급 {{ dailyPay }}원 × {{ days }}일 = <strong style="color: #298861;">{{ basicDailyPay }}원</strong></h5>
            <h5>한 주 주휴수당 {{ holidayPay }}원 × 4주 = <strong style="color: #298861;">(+) {{ holidayTotalPay }}원</strong></h5>
          </div>
        </div>
        <hr style="border: solid 1px gray; margin-top: 0">
        <h4>총 예상 월급은 <b style="color: #e16441;">{{ monthlyPay }}원</b>입니다.</h4>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "MonthlyPay",
    data() {
      return {
        result: false,
        hourlyWage: null,
        startTime: "",
        endTime: "",
        days: "",
        dailyPay: 0,
        monthlyPay: 0,
        dailyTotalTime: 0,
        weeklyTotalTime: 0,
        monthlyTotalTime: 0,
        basicDailyPay: 0,
        holidayPay: 0,
        holidayTotalPay: 0,
        selectedDays: '근무 일수 선택',
        daysList: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15',
          '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']
      }
    },
    methods: {
      selectDays(selectedItem) {
        this.selectedDays = selectedItem;
        this.days = this.selectedDays;
      },
      calMonthlyPay(hourlyWage, startTime, endTime, days) {
        this.dailyTotalTime = 0;

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        const formatStartTime = startTime.substring(11, 13);   // 2020-06-26T09:00:00.000+09:00 -> 09
        const formatEndTime = endTime.substring(11, 13);   // 12

        const workStartTime = parseInt(formatStartTime);   // 9
        const workEndTime = parseInt(formatEndTime);   // 12

        let timeList = new Array(24);
        for(let i=0; i<=23; i++) {
          timeList[i] = Boolean(false);
        }

        if(workStartTime < workEndTime) {   // 근무 시간이 하루 안에 시작 및 종료
          for(let i=workStartTime; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        } else {   // 근무 시간이 자정을 넘어감
          for(let i=workStartTime; i<=23; i++) {
            timeList[i] = Boolean(true);
          }
          for(let i=0; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        }

        let timeMap = new Map();
        for(let i=0; i<=23; i++) {
          if(timeList[i] == Boolean(true)) {
            this.dailyTotalTime++;
            if(i>=6 && i<=21) {
              timeMap.set(i, hourlyWage);   // 주간 기본 시급
            } else {
              timeMap.set(i, (hourlyWage*1.5));   // 야간 수당
            }
          }
        }

        let sum=0;
        timeMap.forEach(function (value) {
          sum += value;
        });
        this.dailyPay = sum;
        this.weeklyTotalTime = this.dailyTotalTime * parseInt(days/4);
        this.monthlyTotalTime = this.dailyTotalTime * days;
        this.basicDailyPay = this.dailyPay * days;

        if(this.weeklyTotalTime >= 15 && this.weeklyTotalTime < 40) {
          this.holidayPay = (this.weeklyTotalTime / 40) * 8 * hourlyWage;
        } else if(this.weeklyTotalTime >= 40) {
          this.holidayPay = 8 * hourlyWage;
        }
        this.monthlyPay = this.basicDailyPay + (this.holidayPay*4);
        this.holidayTotalPay = this.holidayPay * 4;

        this.dailyPay = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.basicDailyPay = this.basicDailyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayPay = this.holidayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayTotalPay = this.holidayTotalPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.monthlyPay = this.monthlyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        this.result = true;
      },
    }
  }
</script>

<style lang="scss" scoped>

  .monthly {
    margin-top: 100px;

    .subject {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .inputForm {
      max-width: 500px;
      margin: auto;
      justify-content: center;
      text-align: center;

      .hourlyWageForm {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

        .form-control {
          margin: 8px;
        }
      }

      .startTime {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

        .form-control {
          margin: 8px;
        }
      }

      .endTime {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

        .form-control {
          margin: 8px;
        }
      }

      .workingDays {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

        div > p {
          width: 402px;
          margin: 3px auto;
          text-align: left;
          font-size: small;
          color: red;
        }
      }
    }

    .btnArea {
      width: 100px;
      margin: 0 auto;
      padding-top: 50px;
      padding-bottom: 50px;
      display: flex;
      justify-content: center;
      font-size: 18px;
    }

    .resultArea {
      width: 100vw;
      margin: auto;
      background-color: lightgrey;
      align-items: center;
      justify-content: space-between;
      font-size: large;

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
