/**
 * 페이징 comp
 */
function PagingView(ops) {
	this._init(ops);
	// default view:
	this.refresh({
		allCount : 1,
		pageNo: 1
	});
}

PagingView.prototype = {
	/**
	 * 페이징 dom 생성 
	 */
	refresh: function(ops) {
		var me = this;
		this._setData(ops);
		//
		var htmlJ = $($.templates(this.htmlTemplate).render(this.data));
		this.htmlContainerJ.empty();
		this.htmlContainerJ.append(htmlJ);
		
		// event:
		htmlJ.find('a').click(function(){
			var comp = $(this); 
			var pageNo = comp.data('page'); 
			if(pageNo) {
				// ui
				comp.parent().find('a').removeClass('active');
				comp.addClass('active');
				//
				var myForm = me.targetFormIdJ;
				myForm.find('[name="schPage"]').val(pageNo);
				
				// load callback
				me.goPageCallback();
			}
		});
	},
	_init: function(ops){
		this.targetFormId = ops.targetFormId; //targetFormId
		this.htmlContainer = ops.htmlContainer; //htmlContainer 
		this.goPageCallback = ops.goPageCallback; //페이지 이동 callback: data load
		//
		this.pagesPerBlock = 10; //매 block에 나타내는 페이지수 
		this.limitCount = 10; //메페이지 item수
		this.htmlTemplate = '#tmpl-pagingTemplate'; //htmlTemplate 
		//
		this._initDomJ();
		//
		var limitCountComp = this.targetFormIdJ.find('[name="schLimitCount"]')
		if(limitCountComp.length > 0) {
			 if(limitCountComp.val() != '') {
				 this.limitCount = limitCountComp.val();
			 }
		}
	},
	_initDomJ : function() {
		this.targetFormIdJ = $(this.targetFormId);
		this.htmlContainerJ = $(this.htmlContainer);
	},
	_setData: function(ops){
		this.allCount = ops.allCount;  		//전체 item수 
		this.pageNo = ops.pageNo; 			//현재 페이지 번호
		this.totalPage = 1;  //전체 페이지수 
		//
		this._calcData();
	},
	_calcData: function() {
		// === totalPage
		var totalPage = parseInt(this.allCount / this.limitCount);
		if((this.allCount % this.limitCount) != 0) {
			totalPage = totalPage + 1;
		}
		if(totalPage < 1) {
			totalPage = 1;
		}
		this.totalPage = totalPage;
		
		// ===block: startIndex, endIndex
		var startIndex = ( parseInt((this.pageNo - 1) / this.pagesPerBlock)) * this.pagesPerBlock + 1; //시작 페이지 번호
		var endIndex =  startIndex + this.pagesPerBlock;  // end 페이지 번호
		endIndex =  endIndex > this.totalPage ? this.totalPage : endIndex - 1;
		
		// === data: 
		var firstIndex = 1;
		var lastIndex = this.totalPage;
		var prevIndex = (this.pageNo - 1);
		if(prevIndex < 1) {
			prevIndex = 1;
		}
		var nextIndex = (this.pageNo + 1);
		if(nextIndex > this.totalPage) {
			nextIndex = this.totalPage;
		}
		var pageIndexList = [];
		for(var i=startIndex; i<=endIndex; i++) {
			pageIndexList.push(i);
		}
		
		var pageNo = this.pageNo;
		this.data = {
			pageNo: pageNo,
			firstIndex : firstIndex,
			lastIndex : lastIndex,
			prevIndex : prevIndex,
			nextIndex : nextIndex,
			pageIndexList : pageIndexList
		};
		console.log('>>> paging: data:');
		console.log(this.data);
		console.log('totalPage=' + totalPage);
		console.log('startIndex=' + startIndex);
		console.log('endIndex=' + endIndex);
	}
};
	

