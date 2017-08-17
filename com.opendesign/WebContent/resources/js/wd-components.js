
	if ( ! Array.prototype.remove ) {	
		Array.prototype.remove = function(from, to) {
			var rest = this.slice((to || from) + 1 || this.length);
			this.length = from < 0 ? this.length + from : from;
			return this.push.apply(this, rest);
		};	
	}

	if (!String.prototype.formatWithJson) {
          String.prototype.formatWithJson = function(jsonObj) {
        	   
	           return this.replace(/{[^{}]+}/g, function(key){
	        	    //var targetAttributeKey = key.replace(/[{}]+/g, "");
	        	    var targetAttributeKeys = key.replace(/[{}]+/g, "").split(".");
	        	    
	        	    var jsonValue = jsonObj;
	        	    for(var i = 0; i<targetAttributeKeys.length ; i++ ){
	        	    	var targetAttributeKey = targetAttributeKeys[i];
	        	    	if( typeof jsonValue[targetAttributeKey] == 'undefined' ){
	        	    		jsonValue = null;
	        	    		break;
	        	    	}
	        	    	
	        	    	jsonValue = jsonValue [targetAttributeKey];
	        	    }
	        	    
	        	    //var jsonValue = jsonObj[targetAttributeKey];
	        	    /* 최현철 : 숫자 0일 경우 빈값이 넘어온다. 수정 160903 */
	        	    if( jsonValue || jsonValue == 0 ){
	        	    	if( jQuery.isFunction( jsonValue  )   ){
	        	    		return jsonValue ( jsonObj );
	        	    	}else{
	        	    		return jsonValue ;
	        	    	}
	        	    }
	        	    return jsonValue  || "";
	        	});
          };
     }

	
	

	/**************************************
	* List Item
	**************************************/	
    function ListItem(opts){
    	
    	this.data = opts.data;
    	this.keyName = opts.keyName;
    	this.key = this.data[this.keyName];
    	
		// Dom Element 추가 (객체는 생성되었으나 아직 아무곳에도 attatch 되지 않음)
        //var formattedHtml = opts.htmlTemplate.formatWithJson( this.data );
        var formattedHtml = $.templates(opts.htmlTemplate).render( this.data );
        this.htmlElement = $( formattedHtml );
        
	}
    
    ListItem.prototype = {       
    	equalsKey: function( findKey ){
    		if( this.key && this.key == findKey ){
    			return true;
    		}
    		return false;
    	}
    }

	/**************************************
	* List View
	**************************************/	
    function ListView(opts){
    	this._data = opts.data || {};
    	this.id = opts.id;
  		this.htmlElement = opts.htmlElement;
  		this.items = [];
  		// 결과가 없습니다.
  		this.htmlElement.siblings('[nodata]').remove(); //여러개 "결과가 없습니다" 처리.
  		this.htmlElementNoData = opts.htmlElementNoData || $('<p class="none">결과가 없습니다.</p>').hide();
  		this.htmlElementNoData.attr('nodata', '1');
  		this.htmlElementNoData.insertAfter(this.htmlElement);
  		this._handleUiNoData();
  		
  		if( this.id  ){
  			window[ this.id ] = this;	
  		}
	}
    
    ListView.prototype = {
    	
    	data: function(key){
    		if( key ){
				return this._data[key];
				
			}
    		
    		return this._data;
    	},
    	
    	putData: function(key, val){
    		var thisData = this._data || {};
    		thisData[key] = val;
    		
    	},
    		
    	_createItem: function( opts ){
    		var node = new ListItem( opts );  				
  			return node;
    		
    	},
    	
    	add: function ( opts ){
    		var node = this._createItem( opts );  				
  			this.items.push(node);
  			
//  		var addIndex= opts.addIndex || this.items.length;
  			this.htmlElement.append( node.htmlElement );
  			this._handleUiNoData();
  			return node;  
    	},
  			
    	addAll: function ( opts ){
    		var dataList = opts.data;
    		var aHtmlTemplate = opts.htmlTemplate;
    		var aKeyName = opts.keyName;
    		
    		var nodes = new Array();
    		var eleArray = new Array();
    		for( var i = 0; i < dataList.length; i++ ){
    			var aData = dataList[i];
    			
    			var node = this._createItem( {
    				keyName:aKeyName
    				, data: aData
    				, htmlTemplate:aHtmlTemplate
    			} );
      			this.items.push(node);
      			nodes.push(node);
      			
      			eleArray.push(node.htmlElement);
    		}
    		
    		this.htmlElement.append( eleArray );
    		this._handleUiNoData();
    		return nodes;
    		
  		},
  			
  		remove: function ( opts ){
  			/* key로 지우기 */
  			if( opts.key ){
  				this._removeByKey(opts.key);
  				this._handleUiNoData();
  				return;
  			}
  			
  			/* index로 지우기 */
  			if( opts.index ){
  				this._removeByKey(opts.index);
  				this._handleUiNoData();
  				return;
  			}
  		},
  		
  		_removeByKey: function(key){
  			var count = this.items.length;
  			for (var i = count - 1; i >= 0; i--) {
  				var aItem = this.items[i];
  				if( aItem.equalsKey( key ) ){
  					aItem.htmlElement.remove();
  					this.items.remove(i);
  				}
  			}
  		},
  		
  		_removeByIndex: function(index){
  			var lastIndex = this.items.length - 1;  
  			if( lastIndex > -1 && lastIndex >= index ){
  				this.items[index].htmlElement.remove();
  				this.items.remove(index);
  			}
  		},

  		find: function ( opts ){
  			/* key로 찾기 */
  			if( opts.key ){
  				return this._findByKey(opts.key);
  			}
  			
  			/* index로 찾기 */
  			if( opts.index ){  				
  				return this._findByIndex(opts.index);
  			}
  			
  			return null;  			
  		},
  		
  		_findByKey: function(key){
  			var count = this.items.length;
  			for (var i = 0; i < count; i++) {
  				var aItem = this.items[i];
  				if( aItem.equalsKey( key ) ){
  					return aItem;
  					
  				}
  			}
  			return null;
  		},
  		
  		_findByIndex: function(index){
  			var lastIndex = this.items.length - 1;  
  			if( lastIndex > -1 && lastIndex >= index ){
  				return this.items[index]; 
  			}
  			return null;
  		},
  		
  		clear : function(){
  			var count = this.items.length;
  			for (var i = count - 1; i >= 0; i--) {
  				$(this.items[i].htmlElement).remove();
  			}
			this.items = [];
			this._uiClear();
  		},
  		/**
  		 * ul dom을 clear
  		 */
  		_uiClear : function() {
  			this.htmlElement.empty();
  			this._handleUiNoData();
  		},
  		/**
  		 * 결과 없음 처리:
  		 */
  		_handleUiNoData: function() {
  			if(this.items.length > 0) {
  				this.htmlElement.show();
  				this.htmlElementNoData.hide();
  			} else {
  				this.htmlElement.hide();
  				this.htmlElementNoData.show();
  			}
  		}
  		
    }
