//------------------------- AjaxComboSelctBox -------------------------
/*
 * $("#exec_cycl").ajaxSelect(
  		{	url : '<c:url value="/util/select/dtalCode"/>',
			masterId : null,
			selectData : { comm_code : "181" },
			defaultValue: execCycl=='월'?'M':(execCycl=='분기')?'Q':'H',
			exceptValue : 'D'
		}
	);
 */
(function($) { 
	$.fn.ajaxSelect = function(settings){
		
		var ajaxcombo = new AjaxCombo();
		ajaxcombo.settings = settings;
		ajaxcombo.slave = this.selector;
		ajaxcombo.initializeBehavior();
	};
	
	var AjaxCombo = function(){
		this.setting;
		this.slave;
	};
	
	AjaxCombo.prototype = {
	
		initializeBehavior: function() {
			this.parentDiv =  (this.slave.split(" ")[1]!=null ? this.slave.split(" ")[0]:null);
			this.firstOption = (this.settings.firstOption!=null ? this.settings.firstOption:null);
			this.paramStr = (this.settings.selectData!=null ? this.settings.selectData:"");
			this.master= (this.settings.masterId!=null ? $("#"+this.settings.masterId,this.parentDiv):null);
		   	this.url = this.settings.url;
		   	this.defaultValue = (this.settings.defaultValue!=null ? this.settings.defaultValue:null);
		   	this.exceptValue = (this.settings.exceptValue!=null ? this.settings.exceptValue:null);
		   	this.async = (this.settings.async!=null ? this.settings.async:true);
		   	this.viewType = (this.settings.viewType!=null ? this.settings.viewType:null);
		    var oThis = this;
		   	if(this.master != null) {
		   		$(this.master).change(function(){ oThis.masterComboChanged();});
	        }
		   	else{
	        	this.getComboList();
		   	}
		},
		masterComboChanged: function(){
			var selectedValue =$(this.master).val();
			this.getComboList(selectedValue);
			
		},
		getComboList : function(selectedValue){
			var data = new Object(this.paramStr);
			var _firstOption = this.firstOption;
			var _defaultValue = this.defaultValue;
			var _viewType = this.viewType;
			var _exceptValue = this.exceptValue;
			
			if(selectedValue!=undefined){
				data.selectedValue = selectedValue;
			}else{
				data.selectedValue = "";
			}
			var el = this.slave.split(" ");
	
			var selobj =$(el[el.length-1],this.parentDiv);
			$.ajax({
				type: "post",
				async: this.async,
				url: this.url,
				data: data,
				success: function(returnData){
					selobj.loadSelect(returnData, _firstOption, _defaultValue, _viewType, _exceptValue); 
					selobj.trigger("change");
				},
				error: function(msg){alert("error:"+msg);}
			});
		}
	};
	
	//SELECT OPTION 삭제  
	$.fn.emptySelect = function() {    
		return this.each(function(){         
			if (this.tagName=='SELECT'){
				this.options.length = 0;
			}
		});    
	};
	
	//SELECT OPTION 등록   
	$.fn.loadSelect = function(response, firstOption, defaultValue, viewType, exceptValue) {  
		
		var optionsDataArray = response;
		
		return this.emptySelect().each(function(){      
			if (this.tagName=='SELECT') { 
				var selectElement = this; 
				if(firstOption!=null){
					var option = new Option(firstOption.text,firstOption.value);   
					if ($.browser.msie) {                   
						selectElement.add(option);           
					}                 
					else {                     
						selectElement.add(option,null);          
					}  
				}
	
				$.each(optionsDataArray, function(idx, optionData){ 
					if(optionData.value != exceptValue){
						var dataValue;
						var dataText ;
						var dataSelValue ;
						dataValue = (optionData.value == null?'':optionData.value);
						dataText = (optionData.text==null?'':optionData.text);
						dataSelValue = (optionData.selValue ==null?'':optionData.selValue);
						
						var option = null;
						if(viewType == 2){
							option = new Option("[ "+dataValue+" ]  "+dataText, dataValue);   
						}else{
							option = new Option(dataText, dataValue);   
						}
						if ($.browser.msie) {        
							selectElement.add(option); 
							if(defaultValue==null||defaultValue==""){
								if(dataValue==dataSelValue) option.selected = true;
							}
							if(defaultValue!=null&&defaultValue!=""){
								if(dataValue==defaultValue) option.selected = true;
							}
						}                 
						else {                     
							selectElement.add(option,null);          
							if(dataValue==defaultValue) option.selected = true;
						}           
					}
				});  
				
			}     
		});
	};
})(jQuery);