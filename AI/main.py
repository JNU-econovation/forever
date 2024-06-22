from typing import List
from fastapi import FastAPI, Depends, File, Request, APIRouter
import uvicorn
from model import upload


app = FastAPI()
app.include_router(upload)

@app.get('/')
async def pdfLoading():
    return {'message' : "pdfLoading"}