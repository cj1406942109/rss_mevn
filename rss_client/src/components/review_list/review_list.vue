<template>
  <div class="review-list">
      <div class="row">
          <div class="alert" role="alert" :class="[submitSuccess?'alert-success':'alert-danger']" v-show="showAlert">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close" @click="hideAlert"><span aria-hidden="true">&times;</span></button>
              {{alertMessage}}
          </div>
          <div class="col-md-3">
              <h3>读后感</h3>
              <ul>
                  <li @click="switchContent(index, menuItem.value)" v-for="(menuItem, index) in sidemenu" :key="menuItem.id" :class="{active:activeMenu==index}">{{menuItem.title}}</li>                  
              </ul>
          </div>
          <div class="col-md-9" ref="reviewWrapper">       
              <ul v-if="review_list && review_list.length > 0" v-show="!submitPage">
                  <li v-for="reviewItem in review_list" :key="reviewItem.id" class="review-item">
                      <div class="title">
                          <span>{{reviewItem.title}}</span>
                      </div>
                      <div class="tags">
                          <span class="glyphicon glyphicon-tags"></span>
                          <span>{{reviewItem.article.sources}}</span>
                          <span>{{reviewItem.article.classifications}}</span>
                      </div>
                      <div class="article">
                          <span><strong>文章：</strong>{{reviewItem.article.title}}</span>
                          <span><strong>作者：</strong>{{reviewItem.article.author}}</span>
                          <span v-if="reviewItem.article.link"><a :href="reviewItem.article.link" target="_blank">查看原文</a></span>
                      </div>
                      <div class="content">
                          <p>{{reviewItem.content}}</p>
                      </div>
                      <div class="time">
                          <span class="glyphicon glyphicon-time"></span>
                          <span>{{reviewItem.post_date | dateFormat}}</span>
                      </div>
                      <button class="btn btn-info" @click="showDetail(reviewItem)">查看详情</button>
                  </li>
              </ul>
              <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">{{detail_review.title}}</h4>
                        </div>
                        <div class="modal-body">
                            <h4>文章信息</h4>
                            <div class="detail">
                                <p><label>标题：</label>{{detail_review.article.title}}</p>
                                <p><label>作者：</label>{{detail_review.article.author}}</p>
                                <p><label>来源：</label>{{detail_review.article.sources}}</p>
                                <p><label>分类：</label>{{detail_review.article.classifications}}</p>
                                <p v-if="detail_review.article.link" style="word-break:break-all"><label>原文链接：</label>{{detail_review.article.link}}</p>
                                <p v-if="detail_review.article.content"><label>文章内容：</label>{{detail_review.article.content}}</p>
                            </div>
                            <h4>我的读后感</h4>
                            <div class="detail">
                                <p><label v-if="detail_review.is_like==true" class="text-primary">推荐该文章</label><label v-else class="text-danger">不推荐该文章</label></p>
                                <p v-if="detail_review.content"><label>详细内容：</label>{{detail_review.content}}</p>
                            </div>
                        </div>     
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="editReview(detail_review)">编辑</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        </div>                   
                    </div>
                </div>
              </div>
              <div class="alert alert-info" style="margin-top:100px" role="alert" v-show="!submitPage && (!review_list || review_list.length==0)"><strong>暂无数据显示！</strong>你可以先提交读后感！</div>
              <div class="review_form" v-show="submitPage">
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
                            <label class="col-md-2 control-label">详细内容<span class="required">&nbsp;*</span></label>
                            <div class="col-md-8">
                                <textarea rows="6" class="form-control" v-model="review.content"></textarea>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-md-2 control-label"></label>
                            <div class="col-md-8 text-right">
                                <button class="btn btn-primary" type="button" :disabled="!formValid" @click="submit">提交</button>
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
// import BScroll from 'better-scroll';
import moment from 'moment';
import config from '@/config';
export default {
    data() {
        return {            
            empty_review: {
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
                user_id: '' //用户id
            },
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
                user_id: '' //用户id
            },
            detail_review: {
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
            review_list: [],            
            sources: ['期刊', '百科', '自媒体'],
            classifications: ['体育', '文化', '游戏', '科技', '财经'],
            activeMenu: 0,          //当前激活的sidemenu下标
            user: '',
            submitPage: false,     //当前是否为提交页面
            showAlert: false,
            alertMessage: '',
            submitSuccess: false  
        }
    },
    computed: {
        formValid: function () {            
            return this.review.article.title && this.review.article.author && this.review.article.sources && this.review.article.classifications && this.review.title && this.review.is_like.toString()!='' && this.review.content;
        }
    },
    filters: {
        dateFormat (value) {
            return moment(value).format('YYYY-MM-DD HH:mm:ss') 
        }
    },
    created() {
        this.user = JSON.parse(sessionStorage.getItem('user'));                
    },
    mounted () {
        this.$nextTick(function (){
            this.switchContent(0, 'all');
        })
    },
    methods: {
        switchContent (i, v) {
            this.activeMenu = i;
            if (v == -1) {
                this.submitPage = true;
            } else {
                this.submitPage = false;
                //此处使用=赋值，或者使用Object.assign()赋值都会导致两个对象互相绑定，修改review的值，empty_review的值会跟着改变
                // this.review = JSON.parse(JSON.stringify(this.empty_review));
                this.$http.post(config.apiHost+'/findAllReviews', {user_id: this.user._id, type: v}).then(response => {
                    var data = response.body;
                    if(data.status!=1){
                        this.alertMessage = data.message;
                        this.showAlert = true;
                    }else{
                        this.$nextTick(function () {
                            this.review_list = data.data;
                        })
                    }                
                }, response => {
                    // error callback 
                    this.alertMessage = '获取数据失败，请稍后重试！';
                    this.showAlert = true;
                });
            }
            
        },
        submit () {            
            if (!this.review._id) {
                //添加
                this.review.user_id = this.user._id;
                this.$http.post(config.apiHost+'/addReview', this.review).then(response => {
                    var data = response.body;
                    this.showAlert = true;
                    this.alertMessage = data.message;
                    if(data.status!=1){
                        this.submitSuccess = false;
                    }else{
                        this.submitSuccess = true;
                        //此处使用=赋值，或者使用Object.assign()赋值都会导致两个对象互相绑定，修改review的值，empty_review的值会跟着改变
                        this.review = JSON.parse(JSON.stringify(this.empty_review));
                        this.switchContent(0, 'all');
                    }
                
                }, response => {
                    // error callback 
                    this.alertMessage = "提交失败，请稍后重试！";
                });
            } else {
                //编辑
                this.$http.post(config.apiHost+'/updateReview', this.review).then(response => {
                    var data = response.body;
                    this.showAlert = true;
                    this.alertMessage = data.message;
                    if(data.status!=1){
                        this.submitSuccess = false;
                    }else{
                        this.submitSuccess = true;
                        //此处使用=赋值，或者使用Object.assign()赋值都会导致两个对象互相绑定，修改review的值，empty_review的值会跟着改变
                        this.review = JSON.parse(JSON.stringify(this.empty_review));
                        this.switchContent(0, 'all');
                    }
                
                }, response => {
                    // error callback 
                    this.alertMessage = "编辑失败，请稍后重试！";
                });
            }
            
        },
        hideAlert () {
            this.showAlert = false;
        },
        showDetail (review) {
            this.detail_review = review;
            $('#myModal').modal('show');
        },
        editReview (old_review) {
            $('#myModal').modal('hide');
            this.switchContent(2, -1);
            this.review = old_review;
        }
    }
}
</script>

<style lang="stylus" scoped>
    .review-list
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
                .review-item 
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
        .alert
            margin-bottom 0
            text-align center
</style>


