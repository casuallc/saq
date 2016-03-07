package com.qing.saq;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;
import com.qing.saq.bean.CViewBean;
import com.qing.saq.bean.EventBean;

public class SAQ {
	private List<CViewBean> viewList = new ArrayList<CViewBean>();
	private List<EventBean> eventList = new ArrayList<EventBean>();
	private View curView;
	private Object container;
	
	public void registe(Object container, View curView) {
		this.container = container;
		this.curView = curView;
	}

	public boolean init() {
		try {
			if(container == null)
				return false;
			
			Class clazz = container.getClass();
			
			Field fields[] = clazz.getDeclaredFields();
			for(Field field : fields) {
				Annotation annos[] = field.getDeclaredAnnotations();
				for(Annotation anno : annos) {
					if(CView.class == anno.annotationType()) {
						CViewBean bean = new CViewBean();
						bean.setName(field.getName());
						bean.setCView((CView) anno);
						bean.setField(field);
						viewList.add(bean);
					}
					
					if(Event.class == anno.annotationType()) {
						EventBean bean = new EventBean();
						bean.setName(field.getName());
						bean.setEvent((Event) anno);
						bean.setField(field);
						eventList.add(bean);
					}
				}
			}
			
			initViews();
			registeEvent();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	void initViews() throws Exception {
		for(CViewBean bean : viewList) {
			bean.getField().set(container, curView.findViewById(bean.getCView().id()));
		}
	}
	
	void registeEvent() throws Exception {
		for(EventBean bean : eventList) {
			for(CViewBean view : viewList) {
				if(view.getName().equals(bean.getEvent().name())) {
					View obj = (View) view.getField().get(container);

					bean.getField();
					switch (bean.getEvent().type()) {
					case CLICK:
						obj.setOnClickListener((OnClickListener) bean.getField().get(container));
						break;

					default:
						break;
					}
					break;
				}
			}
		}
	}
}
