/**
 * Created by wangjing on 2017/5/9.
 */
/*
* 拖拽数据配置
* */
var setting1 = {
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        //beforeDrag: beforeDrag1
       // beforeDrop: beforeDrop1
    }
};


function beforeDrag1(treeId, treeNodes) {
    for (var i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            return false;
        }
    }
    return true;
}
function beforeDrop1(treeId, treeNodes, targetNode, moveType) {
    return targetNode ? targetNode.drop !== false : true;
}



/*
* 删除按钮配置
*
* */

var setting2 = {
    edit: {
        enable: true,
        showRemoveBtn: true,
        removeTitle: "删除节点",
        showRenameBtn:false

    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag2
    }
};



function beforeDrag2(treeId, treeNodes) {
    return false;
}



/*
* 预览试图显示
* */
var setting3 = {
    data: {
        simpleData: {
            enable: true
        }
    }
};



























