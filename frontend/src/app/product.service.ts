import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, Product } from './models';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);
  // Llamamos a /api/... para que Nginx haga proxy al backend (ver nginx.conf)
  private base = '/api/products';

  search(opts: { name?: string; currency?: string; active?: boolean; page?: number; size?: number; sort?: string }): Observable<PageResponse<Product>> {
    let params = new HttpParams();
    if (opts.name) params = params.set('name', opts.name);
    if (opts.currency) params = params.set('currency', opts.currency);
    if (opts.active !== undefined) params = params.set('active', String(opts.active));
    if (opts.page !== undefined) params = params.set('page', String(opts.page));
    if (opts.size !== undefined) params = params.set('size', String(opts.size));
    if (opts.sort) params = params.set('sort', opts.sort);
    return this.http.get<PageResponse<Product>>(this.base, { params });
  }

  create(p: Product) { return this.http.post<Product>(this.base, p); }
  update(id: number, p: Product) { return this.http.put<Product>(`${this.base}/${id}`, p); }
  delete(id: number) { return this.http.delete<void>(`${this.base}/${id}`); }
  get(id: number) { return this.http.get<Product>(`${this.base}/${id}`); }
}
