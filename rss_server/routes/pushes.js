var express = require('express');
var router = express.Router();
const Push = require('../models/push');

const STATUS_ERR = 0;
const STATUS_OK = 1;

//根据用户id获取所有读后感
router.post('/findAllPushes', function(req, res, next) {
    var queryObject = { user_id: req.body.user_id };
    Push.find(queryObject)
        .sort({ article_title: -1 })
        .then(pushes => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': pushes
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
router.post('/getPush', (req, res) => {
    Push.findById(req.params.id)
        .then(push => {
            res.json({
                'status': STATUS_OK,
                'message': '成功获取数据！',
                'data': push
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
//添加推送
router.post('/addPush', (req, res) => {
    var push = req.body;
    Push.create(push, (err, push) => {
        if(err) {
            res.json({
                'status': STATUS_ERR,
                'message': '添加失败，请稍后重试！',
                'data': err
            })
        } else {
            res.json({
                'status': STATUS_OK,
                'message': '添加成功！',
                'data': push
            })
        }
    });
});

module.exports = router;
