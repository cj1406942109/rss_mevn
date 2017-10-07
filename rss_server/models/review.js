const mongoose = require('mongoose')

const articleSchema = mongoose.Schema({
    title: { type: String, required: true },
    author: { type: String, required: true },
    link: { type: String },
    content: { type: String },
    sources: { type: String },
    classifications: { type: String }
})

const reviewSchema = mongoose.Schema({
    title: { type: String, required: true },
    content: { type: String, required: true },
    article: articleSchema,
    is_like: { type: Boolean },
    is_new: { type: Boolean, default: true },
    post_date: { type: Date, default: Date.now },
    user_id: { type: mongoose.Schema.Types.ObjectId, required: true }
})

const Review = module.exports = mongoose.model('Review', reviewSchema)

// review = {
//     title: '', //读后感标题
//     intro: '', //读后感介绍
//     detail: '', //读后感详情
//     article: { //文章
//         title: '', //文章标题
//         author: '', //作者
//         link: '', //链接地址
//         content: '', //文章内容
//         sources: [], //文章来源
//         classifications: [] //文章分类
//     },
//     is_like: '', //是否推荐
//     is_new: true,
//     post_date: '', //发表时间
//     user_id: '' //用户id
// }
