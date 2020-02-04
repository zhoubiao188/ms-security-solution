import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: () => import("../views/login")
  },
  {
    path: "/index",
    name: "Index",
    component: () => import("../views/index")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;

// component: () =>
//       import(/* webpackChunkName: "about" */ "../views/About.vue")
