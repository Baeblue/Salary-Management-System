<template>
  <div class="holiday">
    <div class="subject">
      <h3>주휴수당 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="hourlyWage"/>
      </div>
      <div class="weeklyTotalTimeForm">
        <label for="weeklyTotalTime">주 근무 시간</label>
        <input type="text" class="form-control" placeholder="근무 시간 입력" id="weeklyTotalTime"
               v-model="weeklyTotalTime"/>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calHolidayPay(weeklyTotalTime, hourlyWage)" class="btn btn-success">계산하기</button>
    </div>


    <div class="resultArea" v-show="result">
      <div class="text">
        <div class="cal">
          <div class="title">
            <h5>주휴 수당 계산</h5>
          </div>
          <div class="content">
            <h5 v-if="weeklyTotalTime >=15 && weeklyTotalTime < 40">
              ( <strong style="color: #298861;">{{ weeklyTotalTime }}시간</strong> ÷ 40 ) × 8 × <strong style="color: #298861;">{{ formatHourlyWage }}원</strong>
            </h5>
            <h5 v-else-if="weeklyTotalTime >= 40">
              8 × <strong style="color: #298861;">{{ formatHourlyWage }}원</strong>
            </h5>
          </div>
        </div>
        <hr style="border: solid 1px gray; margin-top: 0">
        <h4>예상 주휴 수당은 <b style="color: #e16441;">{{ holidayPay }}원</b>입니다.</h4>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HolidayPay",
    data() {
      return {
        result: false,
        weeklyTotalTime: null,
        hourlyWage: null,
        formatHourlyWage: null,
        holidayPay: 0,
      }
    },
    methods: {
      calHolidayPay(weeklyTotalTime, hourlyWage) {

        this.holidayPay = 0;

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        if(this.weeklyTotalTime >= 15 && this.weeklyTotalTime < 40) {
          this.holidayPay = (this.weeklyTotalTime / 40) * 8 * hourlyWage;
        } else if(this.weeklyTotalTime >= 40) {
          this.holidayPay = 8 * hourlyWage;
        }

        //this.hourlyWage = hourlyWage.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.formatHourlyWage = hourlyWage.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayPay = this.holidayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        this.result = true;
      }
    }
  }
</script>

<style lang="scss" scoped>

  .holiday {
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
      padding-top: 20px;
      max-width: 500px;
      min-height: 220px;
      margin: auto;

      .hourlyWageForm {

        padding-top: 30px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

        .form-control {
          margin: 8px;
        }
      }

      .weeklyTotalTimeForm {

        padding-top: 30px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

        .form-control {
          margin: 8px;
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
