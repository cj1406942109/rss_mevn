var express = require('express');
var router = express.Router();
const Review = require('../models/review');

const STATUS_ERR = 0;
const STATUS_OK = 1;

//根据用户id获取所有读后感
router.post('/findAllReviews', function(req, res, next) {
    var queryObject = { user_id: req.body.user_id };
    if(req.body.type == 'all') {

    } else if(req.body.type == 'like') {
        queryObject = { user_id: req.body.user_id, is_like: true };
    }
    Review.find(queryObject)
        .sort({ post_date: -1 })
        .then(reviews => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': reviews
            })
        })
        .catch(err => {
            res.json({
                'status': STATUS_ERR,
                'message': '获取数据失败，请稍后重试！',
                'data': err
            })
        });
});
//根据id获取文章信息
router.post('/getReview', (req, res) => {
    Review.findById(req.params.id)
        .then(review => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': review
            })
        })
        .catch(err => {
            res.json({
                'status': STATUS_ERR,
                'message': '获取数据失败，请稍后重试！',
                'data': err
            })
        });
});
//添加读后感
router.post('/addReview', (req, res) => {
    var review = req.body;
    Review.create(review, (err, review) => {
        if(err) {
            res.json({
                'status': STATUS_ERR,
                'message': '上传失败，请稍后重试！',
                'data': err
            })
        } else {
            res.json({
                'status': STATUS_OK,
                'message': '上传成功！',
                'data': review
            })
        }
    });
});
//更新读后感信息
router.post('/updateReview', (req, res) => {
    var new_review = req.body;
    Review.updateOne({ _id: new_review._id }, {
            $set: {
                title: new_review.title,
                intro: new_review.intro,
                detail: new_review.detail,
                "article.title": new_review.article.title,
                "article.author": new_review.article.author,
                "article.link": new_review.article.link,
                "article.content": new_review.article.content,
                "article.sources": new_review.article.sources,
                "article.classifications": new_review.article.classifications,
                is_like: new_review.is_like
            }
        }, {
            new: true //返回更新后的文档，默认为更新前的文档
        })
        .then(review => res.json({
            'status': STATUS_OK,
            'message': '更新成功！',
            'data': review
        }))
        .catch(err => res.json({
            'status': STATUS_ERR,
            'message': '更新失败，请稍后重试！',
            'data': err
        }));
});
//删除读后感
router.post('/deleteReview', (req, res) => {
    Review.findOneAndRemove({
            _id: req.body.id
        })
        .then(review => res.json({
            'status': STATUS_OK,
            'message': `${review.title}删除成功！`,
            'data': review
        }))
        .catch(err => res.json({
            'status': STATUS_ERR,
            'message': '删除失败，请稍后重试！',
            'data': err
        }));
})


module.exports = router;
