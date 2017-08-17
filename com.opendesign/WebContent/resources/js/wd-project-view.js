
	if (! Array.prototype.remove  ) {	
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
	        	    if( jsonValue  ){
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
	* SubjectView
	**************************************/	
    function SubjectView(opts){
    	
    	this.data = opts.data;
    	this.keyName = opts.keyName ;
    	this.key = this.data[this.keyName];
    	
		// Dom Element 추가 (객체는 생성되었으나 아직 아무곳에도 attatch 되지 않음)
        //var formattedHtml = opts.htmlTemplate.formatWithJson( this.data );
        var formattedHtml = $.templates(opts.htmlTemplate).render( this.data );
        this.htmlElement = $( formattedHtml );
        this.containerElement = $( this.htmlElement ).find("ul");
        this.items = [];
        
        // 결과가 없습니다.
  		this.htmlElementNoData = opts.htmlElementNoData || $('<div class="none" style="height:300px;background-color:#f1f1f1; width:210px;display:table-cell;vertical-align:middle;">작품이 없습니다.</div>').hide(); 
  		this.htmlElementNoData.insertAfter(this.containerElement);
  		this._handleUiNoData();
	}
    SubjectView.prototype = {

  			addItem: function ( opts ){ 						
  				
  				var node = new WorkView(opts );
  				$(node.htmlElement).appendTo(this.containerElement);
  				this.items.push(node);
  				
  				this._handleUiNoData();
  			},
  			/**
  	  		 * 결과 없음 처리:
  	  		 */
  	  		_handleUiNoData: function() {
  	  			if(this.items.length > 0) {
  	  				this.containerElement.show();
  	  				this.htmlElementNoData.hide();
  	  			} else {
  	  				this.containerElement.hide();
  	  				this.htmlElementNoData.show();
  	  			}
  	  		}
    }
    

	/**************************************
	* WorkView
	**************************************/	
    function WorkView(opts){
    	
    	this.data = opts.data;
    	this.keyName = opts.keyName ;
    	this.key = this.data[this.keyName];

    	
		// Dom Element 추가 (객체는 생성되었으나 아직 아무곳에도 attatch 되지 않음)
        //var formattedHtml = opts.htmlTemplate.formatWithJson( this.data );
        var formattedHtml = $.templates(opts.htmlTemplate).render( this.data );
        this.htmlElement = $( formattedHtml );
        
        
	}
    WorkView.prototype = {       

    }
    


    
  	/**************************************
  	*  ProjectView
  	**************************************/
  	function ProjectView( opts ){
    	this.id = opts.id;
  		this.htmlElement = opts.htmlElement;
  		
  		this.slideBox = [];
  		this.items = [];  	
  		
  		// 결과가 없습니다.
  		this.htmlElementNoData = opts.htmlElementNoData || $('<p class="none">주제가 없습니다.</p>').hide(); 
  		this.htmlElementNoData.insertAfter(this.htmlElement);
  		this._handleUiNoData();
  		
  		if( this.id  ){
  			window[this.id ] = this;	
  		}
  		
  	}
  	
    ProjectView.prototype = {
    		
  			addSubject: function ( opts ){ 						
  				
  				var node = new SubjectView(opts );  				
  				this.items.push(node);
  				
  				//slideBox not use
  				//this._appendSubjectToSlideBox($(node.htmlElement));
  				$(node.htmlElement).appendTo(this.htmlElement);
  				this._handleUiNoData();
  				return node;  				  				
  			},
  			
  			
  			addSubjects: function ( opts ){
  				var dataList = opts.data;
  				
  				for(var i=0; i< dataList.length ; i++){
  					var subjectOpt = { keyName:opts.keyName , data:dataList[i] , htmlTemplate:opts.htmlTemplate  }; 
  					this.addSubject( subjectOpt );
  				}
  				this._handleUiNoData();
  			},
  			
  			_appendSubjectToSlideBox: function(subjectItem){
  				//마지막 슬라이드 박스에 주제가 6개 꽉 찼다면 슬라이드 박스 하나 더 만듦.
  				//  새로 만든 슬라이드 박스 배열에 추가
  				//	새로운 슬라이드 박스에 주제 추가
  				if((this.items.length - 1) % 6 == 0) {
  					var slideBoxItem = $('<div class="swiper-slide"></div>');
  					slideBoxItem.appendTo(this.htmlElement);
  					this.slideBox.push(slideBoxItem);
  				}
  				var lastSlideBoxItem = this.slideBox[this.slideBox.length - 1];
  				subjectItem.appendTo(lastSlideBoxItem);
  				
  			},

  			addWork: function ( opts ){
  				  				
  				var parentKey = opts.data[opts.parentKeyName];
  				var parentSubject= this.findSubject(parentKey);
  				if( parentSubject){
  					var node = parentSubject.addItem(opts);
  					return node;
  				}  				  				  				
  				return null;
  			},

  			addWorks: function ( opts ){ 				
  				var dataList = opts.data;
  				var parentKeyName = opts.parentKeyName;  				
  				for(var i=0; i< dataList.length ; i++){  					
  					var workOpt = { parentKeyName:opts.parentKeyName , keyName : opts.keyName, data:dataList[i] , htmlTemplate:opts.htmlTemplate  }; 
  					this.addWork( workOpt );
  				}
  				
  			},
  			

  			
  			findSubject: function( key ){
  				for (i = 0; i < this.items.length; i++) {
  					if( this.items[i].key == key ){
  						return this.items[i];
  					}  					
  				}
  				return null;
  			},
  			
  			clear : function(){
  				$( this.htmlElement ).empty();
  				this.items = [];
  				this._handleUiNoData();
  			},  			
  			
  			/** 테이블내 모든 ROW에 대해 실행할 함수를 받아서 실행  **/
  			each : function(fn){
  				var allNodes = this.getAll();
  				for( var i=0; i< allNodes.length ;i++){			
  					fn(allNodes[i]);
  				}
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
    