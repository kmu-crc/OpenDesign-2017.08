/**
 * 카테고리 component:
 * modify: 2016.10.12 
 * modify: 카테고리 다중선택을 단일선택으로 바꾼다.
 */
function CategoryView(ops) {
	this.htmlContainer = ops.htmlContainer; // 카테고리 포함한 dom
	this.hiddenFieldName = ops.hiddenFieldName; //form submit할 이름: categoryCodes
	this.excludeData = ops.excludeData || {}; //exclude할 data
	this.selCateCode = ops.selCateCode || ''; //선택된 카테고리코드: 수정일때
}

CategoryView.prototype = {
	/**
	 * render dom 
	 */
	render: function() {
		//
		this._initComp();
		// event
		this._initEvent();
		return this;
	},
	/**
	 * 선택된 카테고리 set
	 */
	setSelectedCateCode: function(selCateCode) {
		var me = this;
		me.selCateCode = selCateCode;
		me._setSelectedCateCode();
	},
	_initComp: function() {
		this.cateDepth1 = this.htmlContainer.find('[name="cateDepth1"]');
		this.cateDepth2 = this.htmlContainer.find('[name="cateDepth2"]');
		this.cateDepth3 = this.htmlContainer.find('[name="cateDepth3"]');
		this.btnAdd = this.htmlContainer.find('button.btn-add');
		this.ulCateList = this.htmlContainer.find('ul.cate-list');
	},
	_initEvent: function() {
		var me = this;
		
		//readonly
		common.cateReadonly(me.cateDepth2, true);
		common.cateReadonly(me.cateDepth3, true); 
		
		//=== 단일선택 처리
		me.btnAdd.hide();
		
		/**
		 * 카테고리1 load
		 */
		me.initCateDepth1();
		
		/**
		 * 카테고리1 change event
		 */
		me.cateDepth1.on('change', function(e){
			me._onChangeCate1($(this));
		});
		
		/**
		 * 카테고리2 change event
		 */
		me.cateDepth2.on('change', function(e){
			me._onChangeCate2($(this));
		});
		
		
		/**
		 * 카테고리3 change event
		 */
		me.cateDepth3.on('change', function(e){
			me._onChangeCate3($(this));
		});
		
		/**
		 * 카테고리 선택 event
		 */
		me.btnAdd.on('click', function(e){
			me._onClickBtnAdd($(this));
		});
		
	},
	_onClickBtnAdd: function(thisObj){
		var me = this;
		var cateDepth1 = me.cateDepth1;
		var cateDepth2 = me.cateDepth2;
		var cateDepth3 = me.cateDepth3;
		var ulCateList = me.ulCateList;
		var hiddenFieldName = me.hiddenFieldName;
		
		// clear and add
		ulCateList.empty();
		
		//
		//var li = ulCateList.find('li');
		//if( li.length >= 5 ){
		//	alert('카테고리는 최대 5개까지 가능합니다.');
		//	return;
		//}
		
		//
		var val1 = cateDepth1.val();
		var val = val1;
		var txt = cateDepth1.find('option:selected').text();
		var val2 = cateDepth2.val();
		var val3 = cateDepth3.val();
		if( '' != val3 ){
			val = val3;			
			txt = cateDepth3.find('option:selected').text();
		}else{
			if( '' != val2 ){
				val = val2;			
				txt = cateDepth2.find('option:selected').text();
			}
		}
		
		console.log('>>> category select: code='+val+', name='+txt);
		
		// 1.2 validate, 중복 체크
		if(!common.checkCateDup(ulCateList.find('[name="{0}"]'.replace('{0}',hiddenFieldName)), val)) {
			return;
		}
		
		/*
		var aCateli = $($.templates('<li>{{:txt}}<button type="button">x</button><input type="hidden" name="{{:hiddenFieldName}}" value={{:val}} /></li>').render({txt:txt, val:val, hiddenFieldName:hiddenFieldName}));
		aCateli.find('button').on('click', function(e){
			$(this).parent().remove();
		});
		*/
		
		var aCateli;
		
		if( isEmpty(val) ) {
			//val = "*";
			val = "";
			txt = "";
			aCateli = $($.templates('<li style="background: inherit;"></li>').render({txt:txt, val:val, hiddenFieldName:hiddenFieldName}));
		} else {
			aCateli = $($.templates('<li style="text-align: center; padding: 0 6px 0 6px;">{{:txt}}<input type="hidden" name="{{:hiddenFieldName}}" value="{{:val}}" /></li>').render({txt:txt, val:val, hiddenFieldName:hiddenFieldName}));
		}
		
		ulCateList.append(aCateli);
	},
	_onChangeCate3: function(thisObj, callback){
		var me = this;
		//=== 단일선택 처리
		me.btnAdd.trigger('click');
	},
	_onChangeCate2: function(thisObj, callback){
		var me = this;
		var cateDepth3 = me.cateDepth3;
		//
		var val = thisObj.val();
		// readonly
		common.cateReadonly(cateDepth3, true);
		
		if( '' == val ){
			cateDepth3.html('<option value="">전체</option>');
			cateDepth3.prev('input:text').val('전체');
			
			//=== 단일선택 처리
			me.btnAdd.trigger('click');
			return;
		}
		
		me.changeCateDepth(val, cateDepth3, '전체', callback);
	},
	_onChangeCate1: function(thisObj, callback){
		var me = this;
		var cateDepth2 = me.cateDepth2;
		var cateDepth3 = me.cateDepth3;
		//
		var val = thisObj.val();
		// readonly
		common.cateReadonly(cateDepth2, true);
		common.cateReadonly(cateDepth3, true);
		
		if( '' == val ){
			cateDepth2.html('<option value="">전체</option>');				
			cateDepth2.prev('input:text').val('전체');
			
			cateDepth3.html('<option value="">전체</option>');
			cateDepth3.prev('input:text').val('전체');
			
			//=== 단일선택 처리
			me.btnAdd.trigger('click');
			return;
		}
		
		me.changeCateDepth(val, cateDepth2, '전체', callback);
	},
	/**
	 * 선택된 카테고리 set
	 */
	_setSelectedCateCode: function() {
		var me = this;
		var cateCode = me.selCateCode;
		console.log('>>> selCateCode=' + cateCode);
		if(cateCode == '') {
			return;
		}
		
		var cate1 = cateCode.substring(0,3);
		var cate2 = cateCode.substring(0,6);
		var cate3 = cateCode.substring(0,9);
		
		var callback2 = function() {
			if(cate3.length > 6) {
				var cateComp3 = me.cateDepth3.find('option[value="{0}"]'.replace('{0}', cate3)); 
				me._selectCate(cateComp3);
				me._onChangeCate3(cateComp3.closest('select'));
			}
		}
		
		var callback1 = function() {
			if(cate2.length > 3) {
				var cateComp2 = me.cateDepth2.find('option[value="{0}"]'.replace('{0}', cate2)); 
				me._selectCate(cateComp2);
				me._onChangeCate2(cateComp2.closest('select'), callback2);
			}
		}
		
		if(cate1) {
			var cateComp1 = me.cateDepth1.find('option[value="{0}"]'.replace('{0}', cate1)); 
			me._selectCate(cateComp1);
			me._onChangeCate1(cateComp1.closest('select'), callback1);
		}
	},
	/**
	 * 카테고리 선택:
	 * @param cateComp
	 */
	_selectCate: function(cateComp) {
		cateComp.prop('selected', true);
		var txt = cateComp.text();
		cateComp.closest('select').prev('input:text').val(txt);
	},
	changeCateDepth: function(parentCateCode, targetObj, txt, callback) {
		var me = this;
		$.ajax({
			url: '/common/selectCategoryListDepthSub.do', 
	        cache: false,
	        type: 'get',
	        data: {'parentCateCode': parentCateCode},
			success : function(_data){
				if(_data.result) {
		    		targetObj.empty();
		    		targetObj.prev('input:text').val(txt);
		    		
		    		var cateList = _data.result;
		    		var optArray = new Array();
		    		var opt = $('<option/>');
		    		opt.val('');
		    		opt.html(txt);
		    		optArray.push(opt);
		    		
		    		for( var i = 0; i < cateList.length; i++ ){
		    			var aCate = cateList[i];
		    			var aOpt = $('<option/>');
		    			aOpt.val(aCate.categoryCode);
		    			aOpt.html(aCate.categoryName);
		    			optArray.push(aOpt);
		    		}
		    		
		    		targetObj.append(optArray);
		    		
		    		// readonly 처리
		    		if(cateList.length > 0) {
		    			common.cateReadonly(targetObj, false);
		    		}
		    		
		    		//=== 단일선택 처리
					me.btnAdd.trigger('click');
		    		
					//if callback exists
					if(typeof callback != 'undefined') {
						callback();
					}
		    	}
			},
			error : function(req){
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			}
		});
	}
	,initCateDepth1: function() {
		var me = this;
		var excludeData = me.excludeData;
		var cateDepth1 = me.cateDepth1;
		//
		$.ajax({
	        url: '/common/selectCategoryListDepth1.do',
	        type: 'get',
	        data: excludeData,
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
	        	if(_data.result) {
	        		var cateList = _data.result;
	    			var cateComp = cateDepth1;
	    			// clean
	    			
	    			cateComp.html('<option value="">전체</option>'); 
	    			//
	        		if(cateList.length) {
	        			for(var i=0; i<cateList.length; i++) {
	        				var item = cateList[i];
	        				cateComp.append($.templates('<option value="{{:categoryCode}}">{{:categoryName}}</option>').render(item));
	        			}
	        		}
	        		
	        		//=== 단일선택 처리
	        		if(me.selCateCode == '') {
	        			me.btnAdd.trigger('click');
	        		} else {
	        			me._setSelectedCateCode();
	        		}
	        	}
	        }
	    });
	}
};
	

