<template>
  <div class="push-list">
      <div class="row">
          <div class="alert alert-danger" role="alert" v-show="showAlert">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close" @click="hideAlert"><span aria-hidden="true">&times;</span></button>
              {{alertMessage}}
          </div>
          <div class="col-md-3">
              <h3>我的推送</h3>
              <ul>
                  <li @click="getNewPush(index)" v-for="(menuItem, index) in sidemenu" :key="menuItem.id" :class="{active:activeMenu==index}">{{menuItem.title}}</li>                  
              </ul>
          </div>
          <div class="col-md-9" ref="pushWrapper">       
              <ul v-if="push_list && push_list.length > 0">
                  <li v-for="pushItem in push_list" :key="pushItem.id" class="push-item">
                      <div class="title">
                          <span>{{pushItem.article_title}}</span>
                      </div>
                      <div class="tags">
                          <span class="glyphicon glyphicon-tags"></span>
                          <span>{{pushItem.sources}}</span>
                          <span>{{pushItem.classifications}}</span>
                      </div>
                      <div class="article">
                          <span><strong>作者：</strong>{{pushItem.author}}</span>
                          <span v-if="pushItem.link"><a :href="pushItem.link" target="_blank">查看原文</a></span>
                      </div>
                      <div class="content" v-if="pushItem.content">
                          <p>{{pushItem.content}}</p>
                      </div>                      
                  </li>
              </ul>              
              <div class="alert alert-info" style="margin-top:100px" role="alert" v-show="!push_list || push_list.length==0"><strong>暂无数据显示！</strong>你可以点击获取系统推送！</div>              
          </div>
      </div>
  </div>  
</template>

<script>
import config from '@/config';
export default {
    data() {
        return {            
            // "data" : { 
            //     "_id" : { "$oid" : "59da276a31ad8a0de3762bfb" },
            //     "article_id" : "60", 
            //     "article_title" : "德国体育俱乐部体制与群众体育关系的研究", 
            //     "author" : "刘波", 
            //     "classification" : "体育",
            //     "sources" : "期刊", 
            //     "link" : "http://kns.cnki.net/kcms/detail/detail.aspx?filename=TYYK200901016&dbcode=CJFD&dbname=CJFD2009&v=",
            //     "content" : "", 
            //     "pulled_user" : ["1264846302@qq.com", "yx_test@126.com", "1406942109@qq.com", "123@qq.com"] 
            // } ,            
            new_push: {
                article_id:'',
                article_title:'',
                author:'',
                link:'',
                content:'',
                sources:'',
                classification:'',
                user_id:''
            },
            sidemenu: [{
                title: '全部内容',
                value: 'all'
            },{
                title: '获取推送',
                value: -1
            }],
            push_list: [{
                "article_id" : "60", 
                "article_title" : "德国体育俱乐部体制与群众体育关系的研究", 
                "author" : "刘波", 
                "classification" : "体育",
                "sources" : "期刊", 
                "link" : "http://kns.cnki.net/kcms/detail/detail.aspx?filename=TYYK200901016&dbcode=CJFD&dbname=CJFD2009&v=",
                "content" : "",
            }],            
            activeMenu: 0,          //当前激活的sidemenu下标
            user: '',
            showAlert: false,
            alertMessage: ''
        }
    },
    created() {
        this.user = JSON.parse(sessionStorage.getItem('user'));                
    },
    mounted () {
        this.$nextTick(function (){
            this.getPushList(0);
        })
    },
    methods: {
        getNewPush (i) {
            if(i!=0){
                //获取新推送
                this.$http.post('http://116.62.148.24/RSS_Server/RssServlet', {user_id: this.user.email}).then(response => {
                    var data = response.body;
                    if(data.status!=1){
                        this.alertMessage = data.message;
                        this.showAlert = true;
                    }else{
                        this.$nextTick(function () {
                            this.new_push.article_id = data.data.article_id;
                            this.new_push.article_title = data.data.article_title;
                            this.new_push.author = data.data.author;
                            this.new_push.link = data.data.link;
                            this.new_push.content = data.data.content;
                            this.new_push.sources = data.data.sources;
                            this.new_push.classification = data.data.classification;
                            this.new_push.user_id = this.user._id;
                            this.$http.post(config.apiHost+'/addPush', this.new_push).then(response => {
                                var data = response.body;                      
                                console.log(data);
                                getPushList();
                            }, response => {
                                // error callback 
                                console.log(response);
                            });
                        })
                    }
                }, response => {
                    // error callback 
                    this.alertMessage = '获取数据失败，请稍后重试！';
                    this.showAlert = true;
                });
            }
            
        },
        getPushList () {
            this.$http.post(config.apiHost+'/findAllPushes', {user_id: this.user._id}).then(response => {
                var data = response.body;
                if(data.status!=1){
                    this.alertMessage = data.message;
                    this.showAlert = true;
                }else{
                    this.$nextTick(function () {
                        this.push_list = data.data;
                    })
                }                
            }, response => {
                // error callback 
                this.alertMessage = '获取数据失败，请稍后重试！';
                this.showAlert = true;
            });
        },
        hideAlert () {
            this.showAlert = false;
        }
    }
}
</script>

<style lang="stylus" scoped>
    .push-list
        padding 20px 0
        .col-md-3
            padding 50px 0 20px
            background-color #ffffff
            h3
                margin 20px 0
                font-size 20px
                font-weight bold
                line-height 60px
                padding-left 60px
            ul
                list-style-type none
                li  
                    margin 3px 0
                    height 40px
                    font-size 16px
                    line-height 40px
                    cursor pointer
                    padding-left 20px
                    &:hover
                        border-left 1px solid #32c5d2
                        margin-left -1px
                        color #32c5d2
                    &.active
                        border-left 2px solid #32c5d2
                        margin-left -2px
                        color #32c5d2
                        font-weight bold
                    &:last-child
                        margin-top 50px
                        font-weight bold
        .col-md-9          
            background-color #ffffff 
            ul
                list-style-type none
                padding 0
                .push-item 
                    position relative
                    padding 10px 20px
                    margin 20px 0
                    border 1px solid #ccc
                    box-shadow 2px 2px 2px #ccc
                    transition 0.5s
                    &:hover
                        border 1px solid #32c5d2                        
                        transform translateX(10px)
                        box-shadow 2px 2px 2px #32c5d2
                    .title
                        span
                            display inline-block
                            height 40px
                            font-size 18px
                            font-weight bold
                            line-height 40px
                    .tags
                        span 
                            color #999
                            margin 0 5px
                            &:first-child
                                color #32c5d2
                                margin-right 10px
                    .article
                        span
                            margin 0 5px
                            a
                                text-decoration none
                                color #32c5d2
                    .content
                        padding 10px 0 10px 30px
                        p   
                            font-size 14px
                            color #333
                            white-space nowrap
                            overflow-x hidden
                            text-overflow ellipsis
                    .time
                        span
                            color #999
                            margin 0 5px
                            &:first-child
                                font-size 14px
                                color #32c5d2
                    .btn
                        position absolute
                        top 15px
                        right 20px
            .modal-body
                h4
                    margin 20px 10px
                .detail
                    padding-left 30px
            .modal-footer
                .btn-primary
                    border none
                    outline none
                    background-color #32c5d2            
        .alert
            margin-bottom 0
            text-align center
</style>


