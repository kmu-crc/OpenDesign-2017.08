/**
 * error code: sync with CmnConst: RstConst
 */
var ErrCode = {
		/* 성공 */
		V_SUCESS : "1"
		/** 실패 */
		,V_FAIL : "0"
		/** 로그인 필요 */
		,V_NEED_LOGIN : "900"
		/** 포인트 부족 */
		,V_NOT_ENOUGH_POINT : "300"
		/** 이메일 중복 */
		,V_EMAIL_DUP : "400"
		/** 닉네임 중복 */
		,V_UNAME_DUP : "110"
		/** 그룹에 있음 */
		,V_GROUP_CONTAIN : "200"
		/** 그룹에 신청했음 */
		,V_GROUP_REQUESTED : "210"
		/** 파일 size 초과 */
		,V_FILE_SIZE : "500"
};


/**
 * name space
 */
var common = {};

/** ########## prototype define for browser compatibility: ########## */
if (!String.prototype.trim) {
  String.prototype.trim = function () {
    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
  };
}

if (!String.prototype.startsWith) {
    String.prototype.startsWith = function(searchString, position){
      position = position || 0;
      return this.substr(position, searchString.length) === searchString;
  };
}

/** ########## ]]prototype define for browser compatibility: ########## */

/**
 * jsRender setting
 */
$.views.settings.debugMode(false); 	//debug -- throw exception
//$.views.settings.debugMode("");		// product -- render as empty string when error


/**
 * 숫자 콤마(,) 생성
 * @param val
 * @returns ###,###
 */
function formatNumberCommaSeparate( val ){
	//var price = ele.value;	
	
	var price = val;
	price = price.toString();
	price = price.replace(/,/g, '');
	price = parseInt(price, 10);
	if( isNaN( price )) return  '';		
	price = price.toString();		
	var length = price.length;
	if( length < 4 ){
		//ele.value = price;
		return price;
	}
	
	var count = length / 3;
	var slice = new Array();
	for( var i = 0; i < count; ++i ){
		if( i * 3 >= length )
			break;
		slice[i] = price.slice( (i + 1) * - 3, length - ( i * 3 ) );
	}
	
	var revslice = slice.reverse();
	//ele.value = revslice.join(','); 
	return revslice.join(',');
}


/**
 * form value clear
 */
function formValueClear(sel) {
	$(sel).find('form input, textarea').val(''); //clear
}

function extractEventTarget(event){
	event = event || window.event;
	return event.target || event.srcElement;
}

/**
 * 파일 다운로드 
 * fileUrl
 * dispName
 */
common.fileDownload = function(fileUrl, dispName) {
	window.location.href='/common/fileDownload.do?fileUrl=' + fileUrl + '&dispName=' + dispName;
}

/**
 * 카테고리 중복체크:
 */
common.checkCateDup = function(curCateValCompList, cateCode) {
	var curCateValList = new Array();
	$.each(curCateValCompList, function(index,obj) {
		curCateValList.push($(obj).val());
	});
	//console.log(curCateValList);
	var dupCode = false;
	$.each(curCateValList, function(index,val){
		if(cateCode == val) {
			dupCode = true;
		}
	});
	if(dupCode) {
		//alert('카테고리가 중복입니다.');
		return false;
	}
	
	return true;
}

/**
 * category readonly
 */
common.cateReadonly = function(cate, flag) {
	cate.readonly(flag);
	if(flag) {
		cate.prev('input:text').addClass('readonly');
	} else {
		cate.prev('input:text').removeClass('readonly');
	}
}

/**
 * serializeObject
 */
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 문자열에 ... 추가
 */
function addDots(string, limit)
{
  var dots = "..";
  if( string != null && string.length > limit)
  {
    // you can also use substr instead of substring
    string = string.substring(0,limit) + dots;
  }

    return string;
}

/**
 * 파일 확장자 체크
 * acceptSuffixes = 'jpeg, jpg, gif, so on...'
 * @param fileName
 * @param acceptSuffixes
 * @returns
 */
function acceptFileSuffix(fileName, acceptSuffixes) {
	if(fileName == '') {
		return true;
	}
	
	var suffix = fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);
	var accepts = acceptSuffixes.split(',');
	
	for( var i = 0; i < accepts.length; i++ ) {
		var accept = accepts[i].trim();
		if( suffix.toLowerCase() == accept.toLowerCase() ) {
			return true;
		}
	}
	return false;
	
}


/**
 * string 이 비어 있는지 체크
 * @param mixed_var
 * @returns
 */
function isEmpty(mixed_var) {
	  //   example 1: empty(null);
	  //   returns 1: true
	  //   example 2: empty(undefined);
	  //   returns 2: true
	  //   example 3: empty([]);
	  //   returns 3: true
	  //   example 4: empty({});
	  //   returns 4: true
	  //   example 5: empty({'aFunc' : function () { alert('humpty'); } });
	  //   returns 5: false

	  var undef, key, i, len;
	  var emptyValues = [undef, null, false, 0, '', '0'];

	  for (i = 0, len = emptyValues.length; i < len; i++) {
	    if (mixed_var === emptyValues[i]) {
	      return true;
	    }
	  }

	  if (typeof mixed_var === 'object') {
		
		if(  $(mixed_var).attr('placeholder') ) {
			if( $(mixed_var).val() == $(mixed_var).attr('placeholder') )
				return true;
			else if( $(mixed_var).val() == '' )
				return true;
		}
		  
	    if( $(mixed_var).val() != null && $(mixed_var).val() != '' )
	    	return false;
	    else
	    	return true;
	  }

	  return false;
	}


function replaceAll(str, target, replacement) {
	return str.split(target).join(replacement);
}

function isImageFile(filePath) {
	return filePath.match(/\.(jpg|jpeg|png|gif)$/);
}


function nFormatter(num) {
    isNegative = false
    if (num < 0) {
        isNegative = true
    }
    num = Math.abs(num)
    if (num >= 1000000000) {
        formattedNumber = (num / 1000000000).toFixed(1).replace(/\.0$/, '') + 'G';
    } else if (num >= 1000000) {
        formattedNumber =  (num / 1000000).toFixed(1).replace(/\.0$/, '') + 'M';
    } else  if (num >= 1000) {
        formattedNumber =  (num / 1000).toFixed(1).replace(/\.0$/, '') + 'K';
    } else {
        formattedNumber = num;
    }   
    if(isNegative) { formattedNumber = '-' + formattedNumber }
    return formattedNumber;
}


//default img set
/*
 * 1 = 사용자 프로필 
 * 2 = 디자인(작품) 
 * 3 = 디자인(작품) 썸네일
 * 4 = 포트폴리오
 * 5 = 프로젝트 썸네일
 */

function setDefaultImg(obj, num) {
	
	var url = "";
	
	if(num == 1) {
		url = "../resources/image/sub/noimg_profile.png";
	} else if(num == 2) {
		url = "../resources/image/sub/noimg_design_work_main.png";
	} else if(num == 3) {
		url = "../resources/image/sub/noimg_design_work_thumb.png";
	} else if(num == 4) {
		url = "../resources/image/sub/noimg_design_work.png";
	} else if(num == 5) {
		url = "../resources/image/sub/noimg_project_thumb.png";
	}
	
	$(obj).attr("src", url);
	
}



// xss 체크 함수

function xssCheck(str) {     
    str = str.replace(/</gi, "&lt;").replace(/>/gi, "&gt;");
    return str;
  }

