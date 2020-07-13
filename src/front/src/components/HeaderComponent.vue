<template>
  <div class="headerComponent">
    <div class="subject">
      <h1><b>급여 계산기</b></h1>
    </div>
    <nav>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/list"><h4>일급 목록</h4></router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/daily"><h4>일급 계산하기</h4></router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/weekly"><h4>주급 계산하기</h4></router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/monthly"><h4>월급 계산하기</h4></router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/holiday"><h4>주휴수당 계산하기</h4></router-link>
      </div>
    </nav>
  </div>
</template>

<script>
  import EventBus from "../js/EventBus";
  import {EVENT} from "../js/Constants";

  export default {
    name: "HeaderComponent",
    methods: {
      refreshRouterView() {
        EventBus.$emit(EVENT.REFRESH_ROUTER_VIEW);
      }
    }
  };
</script>

<style lang="scss" scoped>

  .headerComponent {
    min-width: 1000px;
    height: 350px;
    background-color: #A5D6A7;

    .subject {
      height: 250px;
      display: flex;
      justify-content: center;
      align-items: center;
      color: white;
    }

    nav {
      display: flex;
      justify-content: center;
      align-items: center;

      .btn {
        width: 230px;
        height: 50px;
        margin: 5px;
        padding-top: 10px;
        background-color: transparent;
        border: 1px white solid;
        text-align: center;
        font-size: 20px;

        h4 {
          margin-bottom: 0;
        }
      }
    }
  }
</style>
