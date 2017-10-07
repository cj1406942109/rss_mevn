<template>
  <div class="review_list">
      <div class="row">
          <div class="col-md-3">
              <h3>读后感</h3>
              <ul>
                  <li @click="switchContent(index, menuItem.value)" v-for="(menuItem, index) in sidemenu" :key="menuItem.id" :class="{active:activeMenu==index}">{{menuItem.title}}</li>                  
              </ul>
          </div>
          <div class="col-md-9">
              <ul v-show="!submitStatus">
                  <li></li>
                  <li></li>
                  <li></li>
                  <li></li>
                  <li></li>
                  <li></li>
                  <li></li>
              </ul>
              <div class="review_form" v-show="submitStatus">
                  <div class="article-wrapper">
                      <h4>我的阅读</h4>
                      <form class="form-horizontal">
                          <div class="form-group">
                            <label class="col-md-2 control-label">文章标题<span class="required">&nbsp;*</span></label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" placeholder="标题" v-model="review.article.title">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-2 control-label">作者<span class="required">&nbsp;*</span></label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" placeholder="作者" v-model="review.article.author">
                            </div>
                          </div>
                          <div class="form-group">
                              <label class="col-md-2 control-label">来源<span class="required">&nbsp;*</span></label>
                              <div class="col-md-8">
                                  <label class="radio-inline" v-for="source in sources" :key="source.id">
                                    <input type="radio" name="sources" :value="source" v-model="review.article.sources">{{source}}
                                  </label>
                              </div>
                          </div>
                          <div class="form-group">
                              <label class="col-md-2 control-label">分类<span class="required">&nbsp;*</span></label>
                              <div class="col-md-8">
                                  <label class="radio-inline" v-for="classification in classifications" :key="classification.id">
                                    <input type="radio" name="classifications" :value="classification" v-model="review.article.classifications">{{classification}}
                                  </label>
                              </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-2 control-label">全文链接</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="如：https://www.baidu.com" v-model="review.article.link">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-2 control-label">文章内容</label>
                            <div class="col-md-8">
                                <textarea rows="6" class="form-control" v-model="review.article.content"></textarea>
                            </div>
                          </div>                          
                      </form>
                  </div>
                  <div class="review-wrapper">
                      <h4>我的读后感</h4>
                      <form class="form-horizontal">
                          <div class="form-group">
                            <label class="col-md-2 control-label">读后感标题<span class="required">&nbsp;*</span></label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" placeholder="标题" v-model="review.title">
                            </div>
                          </div>
                          <div class="form-group">
                              <label class="col-md-2 control-label">推荐该文章<span class="required">&nbsp;*</span></label>
                              <div class="col-md-8">
                                  <label class="radio-inline">
                                    <input type="radio" name="is_like" value="true" v-model="review.is_like">是
                                  </label>
                                  <label class="radio-inline">
                                    <input type="radio" name="is_like" value="false" v-model="review.is_like">否
                                  </label>
                              </div>
                          </div>                                           
                          <div class="form-group">
                            <label class="col-md-2 control-label">详细内容</label>
                            <div class="col-md-8">
                                <textarea rows="6" class="form-control" v-model="review.content"></textarea>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-2 control-label"></label>
                            <div class="col-md-8 text-right">
                                <button class="btn btn-primary" type="button">提交</button>
                            </div>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
      </div>
  </div>  
</template>

<script>
import BScroll from 'better-scroll';
import config from '@/config';

export default {
    data() {
        return {
            review: {
                title: '', //读后感标题
                content: '', //读后感内容
                article: { //文章
                    title: '', //文章标题
                    author: '', //作者
                    link: '', //链接地址
                    content: '', //文章内容
                    sources: '', //文章来源
                    classifications: '' //文章分类
                },
                is_like: '', //是否推荐
                is_new: true,
                post_date: '', //发表时间
                user_id: '' //用户id
            },
            sidemenu: [{
                title: '全部内容',
                value: 'all'
            },{
                title: '我的推荐',
                value: 'like'
            },{
                title: '提交读后感',
                value: -1
            }],            
            sources: ['期刊', '百科', '自媒体'],
            classifications: ['体育', '文化', '游戏', '科技', '财经'],
            activeMenu: 0,          //当前激活的sidemenu下标
            user: '',
            submitStatus: false     //当前是否为提交状态
        }
    },
    created() {
        this.switchContent(0, 'all');
        this.user = JSON.parse(sessionStorage.getItem('user'));        
    },
    methods: {
        switchContent (i, v) {
            this.activeMenu = i;
            if (v == -1) {
                this.submitStatus = true;
            } else {
                this.submitStatus = false;
                // this.$http.post(config.apiHost+'/findAllReviews', {user_id: this.user._id, type: v}).then(response => {

                //     var data = response.body;
                //     if(data.status!=1){

                //     }else{
                        
                //     }
                
                // }, response => {
                //     // error callback 
                    
                // });
            }
            
        }
    }
}
</script>

<style lang="stylus" scoped>
    .review_list
        padding 50px 0
        .col-md-3
            padding 50px 0 100px
            min-height 500px
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
            ul
                list-style-type none
                li 
                    margin 20px 0
                    border 1px solid #ccc
                    box-shadow 2px 2px 2px #ccc
                    min-height 150px
            .review_form
                h4 
                    font-weight bold
                    color #666
                    margin 20px 0
                .required
                    color red
                    font-size 18px
                    font-weight bold
                .btn-primary
                    border none
                    outline none
                    background-color #32c5d2
</style>


