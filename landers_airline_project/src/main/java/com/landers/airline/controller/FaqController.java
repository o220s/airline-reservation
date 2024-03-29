package com.landers.airline.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landers.airline.dto.FaqDto;
import com.landers.airline.dto.FaqParam;
import com.landers.airline.service.FaqService;

@Controller
public class FaqController {

	@Autowired
	FaqService service;
	
	@GetMapping("faqlist.do")
	public String faqlist(Model model, FaqParam param) {
		System.out.println("FaqController faqlist " + new Date());
		System.out.println(param.toString());
				
		// 글목록
		List<FaqDto> list = service.faqlist(param);	
		
		// 글의 총수
		int count = service.allfaq(param);	// 23		
		// 페이지 계산
		int pageFaq = count / 10;	// 2
		if((count % 10) > 0) {
			pageFaq = pageFaq + 1;	// 2 + 1
		}		
		
		model.addAttribute("list", list);
		model.addAttribute("pageFaq", pageFaq);
		model.addAttribute("param", param);
		model.addAttribute("main", "faq/faqlist");
		
		return "customercenter/main";
	}
	
	@GetMapping("faqwrite.do")
	public String faqwrite(Model model) {
		System.out.println("FaqController faqwrite " + new Date());

		model.addAttribute("main", "faq/faqwrite");
		
		return "customercenter/main";
	}
	
	@PostMapping("faqwriteAf.do")
	public String faqwriteAf(FaqDto dto, Model model) {
		System.out.println("FaqController faqwriteAf " + new Date());
		
		boolean isS = service.faqwrite(dto);
		String faqwriteMsg = "FAQWRITE_SUCCESS";
		if(isS == false) {
			faqwriteMsg = "FAQWRITE_FAIL";
		}
		model.addAttribute("faqwriteMsg", faqwriteMsg);
		
		return "message";
	}
	
	@GetMapping("faqdetail.do")
	public String faqdetail(int seq, Model model) {
		System.out.println("FaqController faqdetail " + new Date());
		
		FaqDto dto = service.faqdetail(seq);
		model.addAttribute("dto", dto);
		model.addAttribute("main", "faq/faqdetail");
		
		return "customercenter/main";
	}


	@GetMapping("faqanswer.do")
	public String faqanswer(int seq, Model model) {
		System.out.println("FaqController faqanswer() " + new Date());
		
		FaqDto dto = service.faqdetail(seq);		
		model.addAttribute("faqdto", dto);
		model.addAttribute("main", "faq/faqanswer");
		
		return "customercenter/main";
	}
	
	@PostMapping("faqanswerAf.do")
	public String faqanswerAf(FaqDto dto, Model model) {
		System.out.println("FaqController faqanswerAf() " + new Date());
		
		boolean isS = service.FaqAnswer(dto);
		String answerMsg = "ANSWER_SUCCESS";
		if(isS == false) {
			answerMsg = "ANSWER_FAIL";
		}
		model.addAttribute("faqanswerMsg", answerMsg);
		model.addAttribute("seq", dto.getSeq());
		
		return "message";
	}
	
	@GetMapping("faqupdate.do")
	public String faqupdate(int seq, Model model) {
		System.out.println("FaqController faqupdate() " + new Date());
		
		FaqDto dto = service.faqdetail(seq);		
		model.addAttribute("dto", dto);
		model.addAttribute("main", "faq/faqupdate");
		
		return "customercenter/main";
	}
	
	@GetMapping("faqupdateAf.do")
	public String faqupdateAf(FaqDto dto, Model model) {
		System.out.println("FaqController faqupdateAf() " + new Date());
		
		boolean isS = service.faqupdate(dto);
		String faqupdateMsg = "UPDATE_SUCCESS";
		if(!isS) {
			faqupdateMsg = "UPDATE_FAIL";
		}
		model.addAttribute("faqupdateMsg", faqupdateMsg);
		model.addAttribute("seq", dto.getSeq());
		
		return "message";
	}
	@GetMapping("faqdelete.do")
	public String faqdelete(int seq) {
		System.out.println("FaqController faqdelete() " + new Date());
		
		boolean isS = service.faqdelete(seq);
		if(isS) {
			System.out.println("글삭제 성공!");
		}else {
			System.out.println("글삭제 실패");
		}
		
		return "redirect:/faqlist.do";	
	}
	
}







