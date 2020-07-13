<template>
  <div class="addForm">
    <div v-if="!submitted">
      <div class="subject">
        <h3>근무 시간 입력하기</h3>
      </div>

      <div class="inputForm">
        <div class="hourlyWageForm">
          <label for="hourlyWage">시급</label>
          <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
                 v-model="salary.hourlyWage"/>
        </div>

        <div class="dateTimePicker">
          <div class="startTime">
            <label for="startTime">시작 시간</label>
            <datetime type="datetime" id="startTime"
                      v-model="salary.startTime" placeholder="날짜 선택"
                      value-zone="Asia/Seoul"
                      :minute-step="60"
                      :input-style="'width: 375px; border: transparent'"
                      class="form-control"
            ></datetime>
          </div>

          <div class="endTime">
            <label for="endTime">종료 시간</label>
            <datetime type="datetime" id="endTime"
                      v-model="salary.endTime" placeholder="날짜 선택"
                      value-zone="Asia/Seoul"
                      :minute-step="60"
                      :input-style="'width: 375px; border: transparent'"
                      class="form-control"
            ></datetime>
          </div>
        </div>
      </div>

      <div class="btnArea">
        <button @click="addRow" class="btn btn-success">등록</button>
      </div>
    </div>

    <div class="submitted" v-else>
      <h4>근무시간이 등록되었습니다.</h4>
      <router-link to="/list">
        <button class="btn btn-success">목록 확인</button>
      </router-link>
    </div>
  </div>
</template>

<script>
  import ApiSvc from "../js/ApiSvc";

  export default {
    name: "AddSalary",
    data() {
      return {
        salary: {
          id: 0,
          hourlyWage: "",
          startTime: "",
          endTime: "",
        },
        submitted: false,
      }
    },
    methods: {
      addRow() {
        const requestData = {
          hourlyWage: this.salary.hourlyWage,
          startTime: this.salary.startTime,
          endTime: this.salary.endTime
        };

        ApiSvc.post("/add", requestData)
          .then(res => {
            this.salary.id = res.data.id;
          })
          .catch(e => console.log(e));

        this.submitted = true;
      },
    }
  };
</script>

<style lang="scss" scoped>

  .addForm {
    margin-top: 100px;

    div {

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
        justify-content: center;   // 화면 중앙
        text-align: center;

        .hourlyWageForm {
          padding-top: 50px;
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

        .dateTimePicker {

          .startTime {
            padding-top: 50px;
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

          .endTime {
            padding-top: 50px;
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
    }

    .submitted {
      max-width: 500px;
      height: 400px;
      margin: auto;
      padding-top: 50px;
      justify-content: center;
      text-align: center;

      .btn {
        margin: 100px;
      }
    }
  }
</style>
